package ImageDisposeAndOthers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LineCounter {
    public static void main(String[] args) {
        String projectPath = "D:\\java_work\\Chess_Game\\src"; // 替换为你的项目路径
        File projectDirectory = new File(projectPath);
        int totalLines = countLines(projectDirectory);
        System.out.println("项目代码总行数： " + totalLines);
    }

    public static int countLines(File directory) {
        int lines = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    lines += countLines(file);
                } else if (file.getName().endsWith(".java")) {
                    lines += countLinesInFile(file);
                }
            }
        }
        return lines;
    }

    private static int countLinesInFile(File file) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
