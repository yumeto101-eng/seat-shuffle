import java.util.Scanner;
import java.util.List;

public class MainSeatShuffle {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in, "SHIFT_JIS");
        boolean continueScan = true;
        int rows = 3; // 初期値3列
        int columns = 6; // 初期値6列

        do {

            System.out.println("以下の選択肢から1~3を入力してください。");
            System.out.println("1.【生徒情報の表示】/ 2.【座席表の表示・席替え】/ 3.【終了】");
            System.out.print(" => ");
            int num = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (num) {
                case 1:
                    List<Student> students = InOutCsv.readCsv();
                    for (Student s : students) {
                        System.out.println(s);
                    }
                    break;

                case 2:
                    List<Student> s = InOutCsv.readCsv();

                    // 名前が登録されている生徒数を取得
                    long studentCount = s.stream().filter(student -> !student.getStudentName().isBlank()).count();
                    do {
                        System.out.print("座席の列を指定してください => ");
                        columns = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("座席の行を指定してください => ");
                        rows = scanner.nextInt();
                        scanner.nextLine();

                        if (rows <= 0 || columns <= 0) {
                            System.out.println("行・列には1以上の数値を入力してください。");
                        } else if ((long) rows * columns < studentCount) {
                            System.out.println("生徒数に対して座席数が足りません。");
                        }

                    } while (rows <= 0 || columns <= 0 || (long) rows * columns < studentCount);

                    SeatMap.displaySeatMap(s, rows, columns);
                    Shuffle.selectPlay(scanner, s, rows, columns);
                    break;
                case 3:
                    continueScan = false;
                    break;
            }
        } while (continueScan);
        scanner.close();
    }
}
