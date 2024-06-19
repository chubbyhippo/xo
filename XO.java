import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class XO {
    public static void main(String... args) {
        var zipFile = new File(args[0]);
        var zipFileAbsolutePath = zipFile.getAbsolutePath();
        var folder = new File(zipFileAbsolutePath.substring(0, zipFileAbsolutePath.lastIndexOf(".")));
        runCommand("unzip", "-a", zipFileAbsolutePath);
        Stream.of("build.gradle.kts", "pom.xml", "build.gradle")
                .filter(it -> new File(folder, it).exists())
                .forEach(XO::runCommand);
    }

    private static void runCommand(String... args) {
        try {
            new ProcessBuilder().command(args).inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException ignored) {
        }
    }
}