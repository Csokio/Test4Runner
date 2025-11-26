package org.example.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SvgToPngTranscoder {

    public SvgToPngTranscoder(String inputFile, String outputFile) {
        try {
            File svgFile = new File("C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl\\" + inputFile);
            InputStream svgInputStream = new FileInputStream(svgFile);

            File pngFile = new File("C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl\\" + outputFile);
            OutputStream pngOutputStream = new FileOutputStream(pngFile);

            Transcoder transcoder = new PNGTranscoder();

            TranscoderInput transcoderInput = new TranscoderInput(svgInputStream);
            TranscoderOutput transcoderOutput = new TranscoderOutput(pngOutputStream);

            transcoder.transcode(transcoderInput, transcoderOutput);

            svgInputStream.close();
            pngOutputStream.close();

            System.out.println("SVG has been successfully converted to PNG.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}