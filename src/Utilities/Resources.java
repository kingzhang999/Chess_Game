package Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 资源读取与输出路径的统一入口。
 *
 * <p>贴图与存档模板等只读资源一律通过 classpath 读取，因此无论是从
 * IDE 的 {@code out/} 目录运行，还是从打包好的 jar 运行，都能读到；
 * 而存档、截图这类运行时产物仍然写到 jar 旁边的当前目录。</p>
 */
public final class Resources {

    private Resources() {
    }

    /**
     * 读取 classpath 上的图片资源。
     *
     * @param resourcePath 以 {@code /} 开头的 classpath 路径，例如 {@code /resource/white.jpg}
     */
    public static ImageIcon readIcon(String resourcePath) {
        try (InputStream in = requireStream(resourcePath)) {
            BufferedImage image = ImageIO.read(in);
            if (image == null) {
                throw new IOException("不是可识别的图片格式");
            }
            return new ImageIcon(image);
        } catch (IOException e) {
            throw new IllegalStateException("读取图片资源失败：" + resourcePath, e);
        }
    }

    /**
     * 读取 classpath 上的文本资源。
     *
     * @param resourcePath 以 {@code /} 开头的 classpath 路径
     */
    public static BufferedReader readText(String resourcePath) {
        return new BufferedReader(
                new InputStreamReader(requireStream(resourcePath), StandardCharsets.UTF_8));
    }

    /** 资源是否存在。 */
    public static boolean exists(String resourcePath) {
        try (InputStream in = Resources.class.getResourceAsStream(resourcePath)) {
            return in != null;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 打开一个用于写入的输出文件，父目录不存在时自动创建。
     * 存档模板是打进 jar 的只读资源，因此运行时的存档与截图一律写到当前目录下。
     */
    public static File outputFile(String path) throws IOException {
        File file = new File(path);
        Path parent = file.toPath().toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        return file;
    }

    /** 选择一个可读写的默认目录：目录不存在时退回当前目录。 */
    public static File writableDirectory(String path) {
        File directory = new File(path);
        if (directory.isDirectory() && directory.canWrite()) {
            return directory;
        }
        return new File(".");
    }

    /** 打开存档文件读取；只有确实存在的普通文件才按文件读，否则按 jar 内资源读。 */
    public static BufferedReader openManual(File file) throws FileNotFoundException {
        if (file != null && file.isFile()) {
            return new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        }
        return readText(manualResourcePath());
    }

    /** jar 内置的初始摆法；相对可见的项目路径为 {@code resource/manuals/manual.txt}。 */
    public static String manualResourcePath() {
        return "/resource/manuals/manual.txt";
    }

    private static InputStream requireStream(String resourcePath) {
        InputStream in = Resources.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new IllegalStateException("找不到资源：" + resourcePath
                    + "（如果是从 jar 运行，请确认打包时已把 resource 目录一并放入）");
        }
        return in;
    }
}
