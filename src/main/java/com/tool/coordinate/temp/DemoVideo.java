package com.tool.coordinate.temp;

import cn.hutool.core.io.FileUtil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class DemoVideo {
    //  解决FFmpegFrameGrabber初始化时间长的问题
    static {
        try {
            FFmpegFrameGrabber.tryLoad();
            FFmpegFrameRecorder.tryLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //视频路径
        String videoFileName = "C:\\Users\\EDY\\Desktop\\17617a81e75947cfcc326bc45cbab981.mp4";
        InputStream intInputStream = null;
        //输出的图片路径
        File outPut = new File("C:\\Users\\EDY\\Desktop\\1.jpg");
        try {
            intInputStream = new FileInputStream(new File(videoFileName));
            BufferedImage bufferedImage = grabberVideoFramer(intInputStream);
            ImageIO.write(bufferedImage, "jpg", outPut);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(intInputStream !=null) {
                try {
                    intInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /*try {
            extractAudio(videoFileName,
                    "C:\\Users\\EDY\\Desktop",
                    "C:\\Users\\EDY\\Desktop\\测试.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 获取视频中的图片
     * @param inputStream 视频输入流
     * @return
     * @throws Exception
     */
    public static BufferedImage grabberVideoFramer(InputStream inputStream) throws Exception {
        // 最后获取到的视频的图片缓存
        BufferedImage bufferedImage = null;
        // Frame对象
        Frame frame = null;
        // 标识
        int flag = 0;
        FFmpegFrameGrabber fFmpegFrameGrabber = null;
        try {
            //获取视频文件
            fFmpegFrameGrabber = new FFmpegFrameGrabber(inputStream);
            fFmpegFrameGrabber.start();

            // 获取视频总帧数
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            System.out.println("视频总帧数：" + ftp);
            System.out.println("时长：" + ftp / fFmpegFrameGrabber.getFrameRate() / 60 + "(单位/分钟)");

            //对视屏 帧数处理
            while (flag <= ftp) {
                frame = fFmpegFrameGrabber.grabImage();
                //对视频的第10帧进行处理
                if (frame != null && flag == 100) {
                    // 图片缓存对象
                    bufferedImage = FrameToBufferedImage(frame);
                    break;
                }
                flag++;
            }
        }finally {
            if(fFmpegFrameGrabber != null) {
                fFmpegFrameGrabber.stop();
                fFmpegFrameGrabber.close();
            }
        }
        return bufferedImage;
    }

    private static BufferedImage FrameToBufferedImage(Frame frame) {
        // 创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    /**
     * 获取视频的文件pcm文件地址
     *
     * @param url MP4
     * @return
     * @throws Exception
     */
    public static String getMp4Pcm(String url, String tmpDir) throws Exception {
        Optional<String> pcmPath = Optional.empty();
        try {
            pcmPath = convertMP4toPCM(Paths.get(url), Paths.get(tmpDir));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("转换pcm异常:" + exception.getMessage());
        }
        if (pcmPath.isPresent()) {
            return pcmPath.get();
        } else {
            throw new Exception("视频转换音频失败");
        }
    }

    /**
     * 将单个MP4文件进行片头和片尾歌曲删除后，转换为PCM文件
     *
     * @param mp4Path
     * @param pcmDir
     * @return 转换完成后的pcm文件路径
     */
    public static Optional<String> convertMP4toPCM(Path mp4Path, Path pcmDir) {
        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        // 基于ffmpeg进行pcm转换
        // 基于输入路径的md5值来命名，也可以基于系统时间戳来命名
        String pcmFile = pcmDir.resolve(UUID.randomUUID() + ".pcm").toString();
        ProcessBuilder pcmBuilder =
                new ProcessBuilder(
                        ffmpeg,
                        "-y",
                        "-i",
                        mp4Path.toAbsolutePath().toString(),
                        "-vn",
                        "-acodec",
                        "pcm_s16le",
                        "-f",
                        "s16le",
                        "-ac",
                        "1",
                        "-ar",
                        "16000",
                        pcmFile);
        try {
            // inheritIO是指将 子流程的IO与当前java流程的IO设置为相同
            pcmBuilder.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            System.out.println("ffmpeg将mp4转换为pcm时出错");
            return Optional.empty();
        }
        // 返回pcm文件路径
        return Optional.of(pcmFile);
    }

    /**
     * 根据PCM文件构建wav的header字段
     *
     * @param srate Sample rate - 8000, 16000, etc.
     * @param channel Number of channels - Mono = 1, Stereo = 2, etc..
     * @param format Number of bits per sample (16 here)
     * @throws IOException
     */
    public static byte[] buildWavHeader(int dataLength, int srate, int channel, int format)
            throws IOException {
        byte[] header = new byte[44];

        long totalDataLen = dataLength + 36;
        long bitrate = srate * channel * format;

        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = (byte) format;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channel;
        header[23] = 0;
        header[24] = (byte) (srate & 0xff);
        header[25] = (byte) ((srate >> 8) & 0xff);
        header[26] = (byte) ((srate >> 16) & 0xff);
        header[27] = (byte) ((srate >> 24) & 0xff);
        header[28] = (byte) ((bitrate / 8) & 0xff);
        header[29] = (byte) (((bitrate / 8) >> 8) & 0xff);
        header[30] = (byte) (((bitrate / 8) >> 16) & 0xff);
        header[31] = (byte) (((bitrate / 8) >> 24) & 0xff);
        header[32] = (byte) ((channel * format) / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (dataLength & 0xff);
        header[41] = (byte) ((dataLength >> 8) & 0xff);
        header[42] = (byte) ((dataLength >> 16) & 0xff);
        header[43] = (byte) ((dataLength >> 24) & 0xff);

        return header;
    }

    /**
     * 默认写入的pcm数据是16000采样率，16bit，可以按照需要修改
     *
     * @param filePath
     * @param pcmData
     */
    public static String writeToFile(String filePath, byte[] pcmData) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] header = buildWavHeader(pcmData.length, 16000, 1, 16);
            bos.write(header, 0, 44);
            bos.write(pcmData);
            bos.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 提取音频
     * @param mp4Path MP4地址
     * @param tmpDir 临时文件夹
     * @param resultPath 最终结果音频地址
     * @return 音频地址
     * @throws Exception 异常
     */
    public static String extractAudio(String mp4Path, String tmpDir, String resultPath)
            throws Exception {
        String pcmPath = getMp4Pcm(mp4Path, tmpDir);
        return writeToFile(resultPath, FileUtil.readBytes(pcmPath));
    }
}
