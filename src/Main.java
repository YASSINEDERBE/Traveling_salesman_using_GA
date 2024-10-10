import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        JFrame frame = new JFrame("Traveling Salesman");
      
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}