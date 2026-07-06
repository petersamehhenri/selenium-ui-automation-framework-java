package com.TAF.Media;

import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import com.automation.remarks.video.recorder.VideoRecorder;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class ScreenRecordManager {

    private static final ThreadLocal<VideoRecorder> recorder = new ThreadLocal<>();
    public static final String RECORDINGS_PATH = "TestOutput/recordings/";

    /**
     * Starts screen recording.
     */
    public static void startRecording() {

        if (PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")) {
        try {
            File recordingsDir = new File(RECORDINGS_PATH);
            if (recorder.get() == null) {
                if (!recordingsDir.exists()) {
                    recordingsDir.mkdirs();
                }

                // Configure recorder
                System.setProperty("video.folder", RECORDINGS_PATH);
                System.setProperty("video.enabled", "true");
                System.setProperty("video.mode", "ALL");

                //run in case local only
                if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local")) {
                    recorder.set((VideoRecorder) VideoRecorder.conf());
                    recorder.get().start();
                }

                LogsManager.info("Recording started.");
            } else {
                LogsManager.info("Recording already in progress.");
            }
        } catch (Exception e) {
            LogsManager.error("Failed to start recording: " + e.getMessage());
        }
    }}

    /**
     * Stops the current recording and converts it to MP4.
     *
     * @param testMethodName Test name to save the video file with.
     */
    public static void stopRecording(String testMethodName) {
        try {
            if (recorder.get() != null) {
                String VideoFilePath = String.valueOf(recorder.get().stopAndSave(testMethodName));
                File videoFile = new File(VideoFilePath);
                LogsManager.info("Recording stopped." , VideoFilePath);

                File mp4File = encodeRecording(videoFile);
                if (mp4File != null) {
                    LogsManager.info("Converted to MP4: " + mp4File.getAbsolutePath());
                }
            } else {
                LogsManager.info("No recording is in progress.");
            }
        } catch (Exception e) {
            LogsManager.error("Failed to stop recording: " + e.getMessage());
        } finally {
            recorder.remove();
        }
    }

    /**
     * Convert AVI file to MP4.
     */
    private static File encodeRecording(File sourceFile) {
        if (sourceFile == null || !sourceFile.exists()) {
            LogsManager.error("Source file does not exist.");
            return null;
        }

        File targetFile = new File(
                sourceFile.getParent(),
                sourceFile.getName().replace(".avi", ".mp4")
        );

        try {
            // Audio config
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");

            // Video config
            VideoAttributes video = new VideoAttributes();
            video.setCodec("libx264");

            // Encoding config
            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setAudioAttributes(audio);
            encodingAttributes.setVideoAttributes(video);

            // Encode
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(sourceFile), targetFile, encodingAttributes);

            if (targetFile.exists()) {
                sourceFile.delete();
                LogsManager.info("Deleted original AVI: " + sourceFile.getName());
            }
        } catch (EncoderException e) {
            LogsManager.error("Failed to encode video: " + e.getMessage());
        }

        return targetFile;
    }
}