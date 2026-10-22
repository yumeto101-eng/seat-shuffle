
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Register {
    public static List<Student> registerStudent(Scanner scanner) {
        List<Student> students = new ArrayList<>();
        boolean continueRegister = true;

        do {
            System.out.print("前回の席番号を入力してください（1～17の範囲）: ");
            int studentNumber = scanner.nextInt();
            scanner.nextLine(); // 改行

            if (studentNumber < 1 || 17 < studentNumber) {
                System.out.println("1~17の番号を入力してください");
                continue;
            }

            System.out.print("生徒名を入力してください: ");
            String studentName = scanner.nextLine();

            if (studentName == null || studentName.isEmpty()) {
                System.out.println("生徒名は必須入力です");
                continue;
            }

            Student student = new Student(studentNumber, studentName);
            students.add(student);

            System.out.println("生徒情報が正常に作成されました。");
            System.out.println(student);

            System.out.print("入力を続けますか？（y/n）: ");
            String continueInput = scanner.nextLine();
            if (!continueInput.equalsIgnoreCase("y")) {
                continueRegister = false; // 入力を終了する
            }
        } while (continueRegister); // voidInputがtrueの間、ループを続ける

        return students;
    }

    public static List<Student> addStudents(Scanner scanner, ArrayList<Student> students) {
        List<Student> studentsAdd = new ArrayList<>();
        boolean addMoreStudents = true;

        do {
            System.out.print("前回の席番号を入力してください（1～17の範囲）: ");
            int studentNumber = scanner.nextInt();
            scanner.nextLine(); // 改行

            if (studentNumber < 1 || 17 < studentNumber) {
                System.out.println("1~17の番号を入力してください");
                continue;
            }

            System.out.print("生徒名を入力してください: ");
            String studentName = scanner.nextLine();
            boolean validName = studentName.matches(
                    "[\\p{IsHan}ぁ-ゖァ-ヺ々ー・Ａ-Ｚａ-ｚ０-９　]+");
            if (!validName) {
                System.out.println("生徒名は全角で入力してください");
                continue;
            }

            Student s = new Student(studentNumber, studentName);
            studentsAdd.add(s);

            System.out.println("続けて入力しますか？[y/n] => ");
            String ans = scanner.nextLine();

            if (!ans.equalsIgnoreCase("y")) {
                System.out.println("以下の内容で登録します。");
                for (Student student : studentsAdd) {
                    System.out.println(student);
                }
                System.out.println();
                System.out.println("よろしいですか？[y/n] => ");
                String ans2 = scanner.nextLine();
                if (ans2.equalsIgnoreCase("y")) {
                    for (Student originalStudent : students) {
                        studentsAdd.add(originalStudent);
                    }
                    addMoreStudents = false;
                }
            }
        } while (addMoreStudents);

        return studentsAdd;
    }
}
