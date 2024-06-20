import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class XO {
    public static void main(String... args) {
        var zipFile = new File(args[0]);
        var zipFileAbsolutePath = zipFile.getAbsolutePath();
        var folder = new File(zipFileAbsolutePath.substring(0, zipFileAbsolutePath.lastIndexOf(".")));
        runCommand("unzip", zipFileAbsolutePath);
        var idea = System.getProperty("os.name").contains("Windows") ? "idea.cmd" : "idea";
        Stream.of("build.gradle.kts", "pom.xml", "build.gradle")
                .map(it -> new File(folder, it))
                .filter(File::exists)
                .map(it -> new String[]{idea, it.getAbsolutePath()})
                .forEach(XO::runCommand);
    }

    private static void runCommand(String... args) {
        try {
            new ProcessBuilder().command(args).inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
