import java.util.List;

public class SeatMap {
    // 氏名（漢字のみ）の長さから余白を決める
    public static String centerKanji(String name, int maxLength) {
        if (name.length() > maxLength) {
            name = name.substring(0, maxLength);
        }

        int padding = maxLength / 2 - name.length();

        return " ".repeat(padding)
                + name
                + " ".repeat(padding);
    }

    // 席番号から氏名を取得する
    public static String studentName(List<Student> students, int num) {
        String name = "";
        for (Student s : students) {
            if (s.getStudentNumber() == num) {
                name = s.getStudentName();
                break;
            }
        }

        return name;
    }

    public static void displaySeatMap(List<Student> students, int rows, int columns) {
        int boxWidth = 12;
        int seatsWidth = (boxWidth + 1) * columns + 4; // 罫線の幅を含めた座席表の幅
        int seatsWidthHalf = seatsWidth / 2;
        String teacherDesk = ("%" + seatsWidthHalf + "s").formatted("教卓");
        int seatGroup = 2; // 2グループに分ける
        int groupSeats = columns / seatGroup; // グループごとの席数
        int remainderSeat = columns % seatGroup; // 余りの席数( < setaGroup )
        // 教卓
        System.out.print(teacherDesk);
        System.out.println();
        System.out.println("─".repeat(seatsWidth));

        System.out.println();

        for (int i = 1; i <= rows; i++) {

            // 罫線(上)
            for (int j = 1; j <= seatGroup; j++) {
                System.out.print("┌");
                for (int k = 1; k <= groupSeats; k++) {
                    System.out.print("─".repeat(boxWidth));
                    if (k < groupSeats) {
                        System.out.print("┬");
                    } else if (k == groupSeats && j == seatGroup && remainderSeat != 0) {
                        for (int l = 1; l <= remainderSeat; l++) {
                            System.out.print("┬");
                            System.out.print("─".repeat(boxWidth));
                        }
                    }
                    if (k == groupSeats) {
                        System.out.print("┐");
                        System.out.print("  "); // 通路
                    }
                }
            }
            System.out.println();

            // 罫線 + 席番号
            for (int j = 1; j <= seatGroup; j++) {
                System.out.print("│");
                for (int k = 1; k <= groupSeats; k++) {
                    System.out.print(" ".repeat((boxWidth - 4) / 2));
                    System.out.printf("[%02d]", (i - 1) * columns + (j - 1) * groupSeats + k);
                    System.out.print(" ".repeat((boxWidth - 4) / 2));
                    System.out.print("│");
                    if (k == groupSeats && j == seatGroup && remainderSeat != 0) {
                        for (int l = 1; l <= remainderSeat; l++) {
                            System.out.print(" ".repeat((boxWidth - 4) / 2));
                            System.out.printf("[%02d]", (i - 1) * columns + (j - 1) * groupSeats + k + l);
                            System.out.print(" ".repeat((boxWidth - 4) / 2));
                            System.out.print("│");
                        }
                    }
                    if (k == groupSeats) {
                        System.out.print("  "); // 通路
                    }
                }
            }
            System.out.println();

            // 罫線 + 氏名
            for (int j = 1; j <= seatGroup; j++) {
                System.out.print("│");
                for (int k = 1; k <= groupSeats; k++) {
                    System.out.print(
                            centerKanji(studentName(students, (i - 1) * columns + (j - 1) * groupSeats + k), boxWidth));
                    System.out.print("│");
                    if (k == groupSeats && j == seatGroup && remainderSeat != 0) {
                        for (int l = 1; l <= remainderSeat; l++) {
                            System.out.print(
                                    centerKanji(studentName(students, (i - 1) * columns + (j - 1) * groupSeats + k + l),
                                            boxWidth));
                            System.out.print("│");
                        }
                    }
                    if (k == groupSeats) {
                        System.out.print("  "); // 通路
                    }
                }
            }
            System.out.println();

            // 罫線(下)
            for (int j = 1; j <= seatGroup; j++) {
                System.out.print("└");
                for (int k = 1; k <= groupSeats; k++) {
                    System.out.print("─".repeat(boxWidth));
                    if (k < groupSeats) {
                        System.out.print("┴");
                    } else if (k == groupSeats && j == seatGroup && remainderSeat != 0) {
                        System.out.print("┴");
                        System.out.print("─".repeat(boxWidth));
                    }
                    if (k == groupSeats) {
                        System.out.print("┘");
                        System.out.print("  "); // 通路
                    }
                }
            }
            System.out.println();

        }
    }
}
