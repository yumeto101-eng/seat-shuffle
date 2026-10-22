import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InOutCsv {

    private static final Path CSV_PATH = Path.of("students.csv");

    public static void inputCsv(List<Student> students) {
        try {
            writeCsv(students);

            List<Student> loadedStudents = readCsv();

            for (Student student : loadedStudents) {
                System.out.println(
                        student.getStudentNumber() + "：" + student.getStudentName());
            }

        } catch (IOException e) {
            System.out.println("ファイル処理に失敗しました。");
            e.printStackTrace();
        }
    }

    // CSV出力
    public static void writeCsv(List<Student> students)
            throws IOException {

        try (
                BufferedWriter writer = Files.newBufferedWriter(
                        CSV_PATH,
                        StandardCharsets.UTF_8)) {
            // 見出し
            writer.write("id,name");
            writer.newLine();

            for (Student student : students) {
                writer.write(
                        student.getStudentNumber() + "," + student.getStudentName());
                writer.newLine();
            }
        }
    }

    // CSV読み込み
    public static List<Student> readCsv()
            throws IOException {

        List<Student> students = new ArrayList<>();

        try (
                BufferedReader reader = Files.newBufferedReader(
                        CSV_PATH,
                        StandardCharsets.UTF_8)) {
            // 1行目の見出しを読み飛ばす
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",", -1);

                if (columns.length != 2) {
                    System.out.println(
                            "形式が正しくないため読み飛ばします：" + line);
                    continue;
                }

                int id = Integer.parseInt(columns[0]);
                String name = columns[1];

                students.add(new Student(id, name));
            }
        }

        return students;
    }
}
