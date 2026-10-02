package Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 资源读取与输出路径的统一入口。
 *
 * <p>只读资源（贴图、初始摆法）先按 classpath 找，找不到再退回到项目目录下的
 * {@code resource/} 文件。这样两种运行方式都能工作：</p>
 * <ul>
 *   <li>双击 jar：资源打进包里，classpath 命中；</li>
 *   <li>在 IDE 里直接运行：{@code resource/} 通常不在 classpath 上，回退到文件读取。</li>
 * </ul>
 *
 * <p>存档、截图这类运行时产物一律写到当前目录（jar 或项目目录旁边），
 * 父目录不存在时自动创建。</p>
 */
public final class Resources {

    /** jar 内置的初始摆法。 */
    public static final String MANUAL_RESOURCE = "/resource/manuals/manual.txt";

    /** 回退查找资源文件时使用的目录，可用 {@code -Dchess.resourceDir=...} 覆盖。 */
    private static final String RESOURCE_DIR =
            System.getProperty("chess.resourceDir", "resource");

    private Resources() {
    }

    /**
     * 读取图片资源。
     *
     * @param resourcePath 以 {@code /} 开头的资源路径，例如 {@code /resource/white.jpg}
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
     * 读取文本资源。
     *
     * @param resourcePath 以 {@code /} 开头的资源路径
     */
    public static BufferedReader readText(String resourcePath) {
        return new BufferedReader(
                new InputStreamReader(requireStream(resourcePath), StandardCharsets.UTF_8));
    }

    /** 资源是否可读（classpath 或项目目录下任一命中即可）。 */
    public static boolean exists(String resourcePath) {
        try (InputStream in = open(resourcePath)) {
            return in != null;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 打开存档读取；只有确实存在的普通文件才按文件读，否则读内置的初始摆法。
     */
    public static BufferedReader openManual(File file) throws IOException {
        if (file != null && file.isFile()) {
            return new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        }
        return readText(MANUAL_RESOURCE);
    }

    /**
     * 打开一个用于写入的输出文件，父目录不存在时自动创建。
     * 存档与截图是运行时产物，因此一律写到当前目录下。
     */
    public static File outputFile(String path) throws IOException {
        File file = new File(path == null || path.isBlank() ? "." : path);
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

    /**
     * 先按 classpath 找资源，找不到再按项目目录下的文件找。
     * 资源路径形如 {@code /resource/white.jpg}，因此回退时对应的磁盘文件是
     * 当前工作目录下的 {@code resource/white.jpg}。
     *
     * @return 资源流；两处都没有时返回 {@code null}
     */
    private static InputStream open(String resourcePath) throws IOException {
        InputStream fromClasspath = Resources.class.getResourceAsStream(resourcePath);
        if (fromClasspath != null) {
            return fromClasspath;
        }
        String relativePath = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
        //能按“当前工作目录/相对路径”直接找到最好；找不到再试配置的资源目录。
        for (String candidate : new String[]{relativePath, RESOURCE_DIR + "/" + relativePath}) {
            File asFile = new File(candidate);
            if (asFile.isFile()) {
                return new FileInputStream(asFile);
            }
        }
        return null;
    }

    /** 与 {@link #open} 相同，但找不到时抛出带排查提示的异常。 */
    private static InputStream requireStream(String resourcePath) {
        try {
            InputStream in = open(resourcePath);
            if (in == null) {
                throw new IllegalStateException("找不到资源：" + resourcePath
                        + "（从 jar 运行时请确认打包时已把 resource 目录放入；"
                        + "在 IDE 里运行请确认工作目录是项目根目录，"
                        + "或把 resource 目录标记为资源根）");
            }
            return in;
        } catch (IOException e) {
            throw new IllegalStateException("打开资源失败：" + resourcePath, e);
        }
    }
}
