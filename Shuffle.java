import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

public class Shuffle {
    public static void selectPlay(Scanner scanner, List<Student> students, int rows, int columns) throws Exception {
        int seatsCount = rows * columns;
        int studentCount = 0;
        for (Student student : students) {
            if (!student.getStudentName().isBlank()) {
                studentCount++;
            }
        }
        int empSeats = seatsCount - studentCount;
        List<Integer> seatIgnoreNums = new ArrayList<>();
        System.out.println("座席数：" + seatsCount);
        System.out.println("空席数：" + empSeats);

        System.out.print("席替えしますか？[y/n] => ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("y")) {
            if (empSeats > 0) {
                System.out.print("空席指定はありますか？【y/n】=> ");
                String ans1 = scanner.nextLine();
                if (ans1.equalsIgnoreCase("y")) {
                    boolean addMoreSheats = true;
                    do {
                        System.out.print("席番号を入力してください => ");
                        int seatIgnoreNum = scanner.nextInt();
                        scanner.nextLine();
                        seatIgnoreNums.add(seatIgnoreNum);

                        if (seatIgnoreNums.size() >= empSeats) {
                            addMoreSheats = false;
                            break;
                        }

                        System.out.print("他にも空席指定はありますか？【y/n】");
                        String ans2 = scanner.nextLine();

                        if (ans2.equalsIgnoreCase("y")) {
                            addMoreSheats = true;
                        } else {
                            addMoreSheats = false;
                        }
                    } while (addMoreSheats);
                }
            }
            List<Student> students2 = new ArrayList<>();
            try {
                students2 = play(students, seatIgnoreNums, rows, columns);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                return;
            }
            
            SeatMap.displaySeatMap(students2, rows, columns);
            System.out.print("この座席で上書きしますか？【y/n】=> ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                // 席番号の昇順に並べ替えて、CSVに保存する
                students2.sort(
                        (a, b) -> Integer.compare(
                                a.getStudentNumber(),
                                b.getStudentNumber()));

                InOutCsv.writeCsv(students2);

                System.out.println("座席情報を更新しました。\n");
            }
        }
    }

    public static List<Student> play(List<Student> students, List<Integer> seatIgnoreNums, int rows, int columns) {
        int seatsCount = rows * columns;

        // 名前が登録されている生徒だけ
        List<Student> candidates = new ArrayList<>();

        for (Student student : students) {
            if (!student.getStudentName().isBlank()) {
                candidates.add(student);
            }
        }

        // 座席数不足の判定
        if (candidates.size() > seatsCount) {
            throw new IllegalArgumentException("座席数が生徒数より少ないため席替えできません。");
        }

        // 足りない座席数だけ空席追加
        while (candidates.size() < seatsCount) {
            candidates.add(new Student(0, ""));
        }

        // 座席番号のリストを作成
        List<Integer> nums = new ArrayList<>();

        for (int i = 1; i <= seatsCount; i++) {
            nums.add(i);
        }

        boolean hasSameNum;
        int shuffleCount = 0;
        final int MAX_SHUFFLE = 1000;

        System.out.println("席替えを行います");

        do {
            Collections.shuffle(nums);
            shuffleCount++;
            hasSameNum = false;

            for (int i = 0; i < seatsCount; i++) {
                Student student = candidates.get(i);

                if (!student.getStudentName().isBlank() && (nums.get(i) == student.getStudentNumber()
                        || seatIgnoreNums.contains(nums.get(i)))) {
                    hasSameNum = true;
                    break;
                }
            }
        } while (hasSameNum && shuffleCount < MAX_SHUFFLE);

        // MAX_SHUFFLE回数に達した場合
        if (hasSameNum) {
            throw new IllegalStateException("席替えに失敗しました。座席数や空席指定を変更するか、もう一度お試しください。");
        }
        
        // 新しい生徒リスト
        List<Student> students2 = new ArrayList<>();

        for (int i = 0; i < seatsCount; i++) {
            Student student2 = new Student(nums.get(i), candidates.get(i).getStudentName());
            students2.add(student2);
        }

        System.out.println("席替えが完了しました");

        return students2;
    }
}
