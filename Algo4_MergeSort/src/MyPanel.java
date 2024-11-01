import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {

    ArrayList<Integer> intList = new ArrayList<Integer>();
    Random random = new Random();
    int rand;
    int temp;
    boolean sorted = false;
    private int linesDrawn = 0;
    int size = 300;
    private long startTime;
    private long elapsedTime;
    private double elapsedSeconds;
    int opCount;


    public MyPanel() {
    	
        this.setPreferredSize(new Dimension(size, size));

        for (int i = 0; i < size; i++) {
            rand = random.nextInt(0, size);
            intList.add(rand);
        }
        
        startTime = System.currentTimeMillis(); // Record the start time

        // Start a thread to sort the list after a short delay
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Delay for 500 milliseconds (adjust as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mergeSort(intList, 0, intList.size() - 1); // Start Merge Sort
            sorted = true;
            repaint(); // Trigger repaint after sorting
            
            long endTime = System.currentTimeMillis(); // Record the end time
            elapsedTime = endTime - startTime;
            elapsedSeconds = (double) elapsedTime / 1000.0;
            System.out.println("Sorting completed in " + elapsedSeconds + " seconds");
            System.out.println(opCount + " operations");
            
        }).start();
        
    }

    private void mergeSort(ArrayList<Integer> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }
    
    private void merge(ArrayList<Integer> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<Integer> leftArray = new ArrayList<>(list.subList(left, left + n1));
        ArrayList<Integer> rightArray = new ArrayList<>(list.subList(mid + 1, mid + 1 + n2));

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray.get(i) <= rightArray.get(j)) {
                list.set(k++, leftArray.get(i++));
            } else {
                list.set(k++, rightArray.get(j++));
            }
            
            opCount++;
            
         // Pause to show the merge step
            repaint();
            try {
                Thread.sleep(1); // Adjust delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        

        while (i < n1) {
            list.set(k++, leftArray.get(i++));
            
            opCount++;
            
         // Pause to show the merge step
            repaint();
            try {
                Thread.sleep(1); // Adjust delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (j < n2) {
            list.set(k++, rightArray.get(j++));
            
            opCount++;
            
         // Pause to show the merge step
            repaint();
            try {
                Thread.sleep(4); // Adjust delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.BLACK); // Fill the panel with black
        g2D.fillRect(0, 0, getWidth(), getHeight());
        
        g2D.setStroke(new BasicStroke(1));
        
        g2D.setColor(Color.YELLOW);
        if(!sorted) {
        	g2D.drawString("Time: (running)", 10, 20);
        } else {
            g2D.drawString("Time: " + elapsedSeconds + " s", 10, 20);
        }
        g2D.drawString("Operations: " + opCount, 10, 40);

        if (!sorted) {
            g2D.setColor(Color.WHITE);
            // Draw the unsorted white lines
            for (int i = 0; i < intList.size(); i++) {
                g2D.drawLine(i, getHeight(), i, intList.size() - intList.get(i));
            }
        } else {
            g2D.setColor(Color.WHITE);
            // Draw the sorted white lines
            for (int i = 0; i < intList.size(); i++) {
                g2D.drawLine(i, getHeight(), i, intList.size() - intList.get(i));
            }

            g2D.setColor(Color.RED);
            // Draw the sorted red lines one by one, creating a growing effect
            for (int i = 0; i < linesDrawn; i++) {
                g2D.drawLine(i, getHeight() - intList.get(i), i, getHeight());
            }
            
            
            if (linesDrawn < intList.size()) {
                linesDrawn++; // Increase the number of lines drawn
                repaint(); // Trigger repaint to continue the animation
            }
        }
        
    }
}
