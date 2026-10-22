public class Student {

    private int studentNumber;
    private String studentName;

    // コンストラクタ
    public Student(int studentNumber, String studentName) {
        setStudentNumber(studentNumber);
        setStudentName(studentName);
    }

    // ゲッター
    public int getStudentNumber() {
        return this.studentNumber;
    }

    public String getStudentName() {
        return this.studentName;
    }

    // セッター
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setStudentName(String studentName) {
        boolean validName = studentName.matches(
                "[\\p{IsHan}ぁ-ゖァ-ヺ々ー・Ａ-Ｚａ-ｚ０-９　]+");

        if (studentName == null || studentName.isEmpty()) {
            this.studentName = "";
        } else if (!validName) {
            throw new IllegalArgumentException("全角で入力してください。");
        } else {
            this.studentName = studentName;
        }
    }

    // 生徒情報を表示するメソッド toString()をオーバーライド
    public String toString() {
        String fmtNumber = ("%02d").formatted(getStudentNumber());
        return fmtNumber + "：" + getStudentName();
    }
}
