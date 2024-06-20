import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class XO {
    public static void main(String... args) {
        var zipFile = new File(args[0]);
        var zipFileAbsolutePath = zipFile.getAbsolutePath();
        System.out.println(zipFileAbsolutePath);
        var folder = new File(zipFileAbsolutePath.substring(0, zipFileAbsolutePath.lastIndexOf(".")));
        System.out.println(folder.getAbsolutePath());
        runCommand("unzip", zipFileAbsolutePath);
        String idea;
        if (System.getProperty("os.name").contains("Windows")) {
            idea = "idea.cmd";
        } else {
            idea = "idea";
        }

        Stream.of("build.gradle.kts", "pom.xml", "build.gradle")
                .map(it -> new File(folder, it))
                .filter(File::exists)
                .peek(file -> System.out.println(file.getAbsolutePath()))
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
