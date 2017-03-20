/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Thushan.Animate;
import Thushan.ReadFromFile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Design
 */
public class DAA extends javax.swing.JFrame {

    /**
     * Creates new form DAA
        */ 
    
    private final Color unsorted = Color.decode("#2c3e50");
    private final Color sorted = Color.decode("#27ae60");
    private final Color from = Color.decode("#c0392b");
    private final Color to = Color.decode("#f39c12");
    private final Color temp = Color.decode("#7f8c8d");
    
    private String displayOrder = "";
    private String displayCurrentValue = "";
    private String displayNewValue = "";
    private String displayCurrentIndex = "";
    private String displayNewIndex = "";
    private String displayAlgorithm = "";
    private String displayStatus = "";
    private String displayLastMinimumValue = "";
    private String displayLastMinimumIndex = "";
    
    private boolean isAscendingOrder = true;
    private boolean isSelectionSort = true;
    
    private final String intro = "<html>\n" +
    "<br>\n" +
    "<h1  color=\"#3498db\">" +
    "Algorithm simulator<br>\n" +
    "<br>\n" +
    "</h1><h2 color=\"#bdc3c7\">" +
    "for<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "</h2><h2 color=\"#2ecc71\">" +
    "Selection sort<br>\n" +
    "<br>\n" +
    "</h2><h3 color=\"#ffffff\">" +
    "and<br>\n" +
    "</h3><h2 color=\"#2ecc71\">" +
    "<br>\n" +
    "Bubble Sort<br>\n" +
    "</h2><h3 color=\"#95a5a6\">" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "DAA Assignment 2014\n" +
    "</h3><h4>" +
    "IT13011130\n" +
    "Gunathilaka D.D.T.M.\n" +
    "</h4><h5>" +
    "Batch 01 C11 - Metro\n" +
    "</h5><h6>" +
    "9/15/2014\n" +
    "</html>";


    private final String bubbleSortDescription = "<html>\n" +
    "<br>\n" +
    "<font color=\"#f1c40f\">" +
    "Summary -<br><br>\n" +
    "<font color=\"#ffffff\">" +
    "<br>\n" +
    "For each element in the list<br>\n" +
    "<font color=\"#bdc3c7\">" +
    "<br>\n" +
    "Compare with the element to the right<br>\n" +
    "<br>\n" +
    "<font color=\"#ffffff\">" +
    "If they are out of order<br>\n" +
    "<br>\n" +
    "<font color=\"#bdc3c7\">" +
    "Swap elements<br>\n" +
    "<br>\n" +
    "<br>\n" +
    "<font color=\"#2ecc71\">" +
    "Best case O(n )<br>\n" +
    "Average case O(n² )<br>\n" +
    "Worst case O(n² )<br>\n" +
    "<font color=\"#3498db\">" +
    "<br>\n" +
    "Worst case senario - Backword sorted list<br>\n" +
    "<br>\n" +
    "# of passthroughts = no of elements in the list - 1<br>\n" +
    "Largest elements bubble up to the right quickly<br>\n" +
    "<br>\n" +
    "<font color=\"#16a085\">" +
    "To optinimize the algorithm <br>\n" +
    "<br>\n" +
    "Check if the array is already sorted after each iteration<br>\n" +
    "<br>\n" +
    "After m iterations  through entire array the right most m elements are guaranteed to be in there correct places<br>\n" +
    "<font color=\"#ffffff\">" +  
    "<br>\n" +
    "procedure bubbleSort( A : list of sortable items )<br>\n" +
    "    n = length(A)<br>\n" +
    "    repeat<br>\n" +
    "       newn = 0<br>\n" +
    "       for i = 1 to n-1 inclusive do<br>\n" +
    "          if A[i-1] > A[i] then<br>\n" +
    "             swap(A[i-1], A[i])<br>\n" +
    "             newn = i<br>\n" +
    "          end if<br>\n" +
    "       end for<br>\n" +
    "       n = newn<br>\n" +
    "    until n = 0<br>\n" +
    "end procedure<br>\n" +
    "<font color=\"#2ecc71\">" +
    "<br>\n" +
    "Source - " +
    "<font color=\"#3498db\">" +
    "http://en.wikipedia.org/wiki/Bubble_sort<br>\n" +
    "</html>";

    private final String selectionSortDescription = "<html>\n" +
    "<br>\n" +
    "<font color=\"#f1c40f\">" +
    "Summary -<br><br>\n" +
    "<font color=\"#ffffff\">" +
    "<br>\n" +
    "For each element in the list except last element<br>\n" +
    "<br>\n" +
    "<font color=\"#bdc3c7\">" +
    "Get first element of the unsorted portion as the current minimum to compare<br>\n" +
    "<br>\n" +
    "<font color=\"#ffffff\">" +
    "Iterate over each element in the unsorted portion<br>\n" +
    "<br>\n" +
    "<font color=\"#bdc3c7\">" +
    "Compare current minimum with the current unsorted element<br>\n" +
    "<br>\n" +
    "<font color=\"#ffffff\">" +
    "If the current unsorted element is the most suitable candidate remember it as the new current minimum<br>\n" +
    "<br>\n" +
    "<font color=\"#bdc3c7\">" +
    "If the first element of unsorted portion is not equal to the current minimum swap it<br>\n" +
    "<br>\n" +
    "<font color=\"#2ecc71\">" +
    "Best case O(n² )<br>\n" +
    "Average case O(n² )<br>\n" +
    "Worst case O(n² )<br>\n" +
    "<br>\n" +
    "<font color=\"#3498db\">" +
    "Each iteration sort one element<br>\n" +
    "<br>\n" +
    "After i iterations sorted portion must have i elements<br>\n" +
    "<br>\n" +
    "At the last iteration last element is already sorted<br>\n" +
    "<font color=\"#ffffff\">" +  
    "<br>\n" +
    " function selectionSort(list[1..n], k)<br>\n" +
    "     for i from 1 to k<br>\n" +
    "         minIndex = i<br>\n" +
    "         minValue = list[i]<br>\n" +
    "         for j from i+1 to n<br>\n" +
    "             if list[j] < minValue<br>\n" +
    "                 minIndex = j<br>\n" +
    "                 minValue = list[j]<br>\n" +
    "         swap list[i] and list[minIndex]<br>\n" +
    "     return list[k]<br>\n" +
    "<br>\n" +
    "<font color=\"#2ecc71\">" +
    "Source -<br>\n" +
    "<font color=\"#3498db\">" +
    "http://en.wikipedia.org/wiki/Selection_algorithm<br>\n" +
    "</html>";

    private final String algorithm = "<html>\n" +
    "<h3  color=\"#ecf0f1\">" +
    " An Algorithm<br>" +
    "</h3><h4  color=\"#ecf0f1\">" +
    " is a process or set of rules to be followed in calculations or other problem-solving operations, especially by a computer.\n" +
    "<h4></html>";
    
    private final String algo = "<html>\n" +
    "<font color=\"#ffffff\">" +
    "Selection sort is one of the O(n2) sorting algorithms, which makes it quite inefficient for sorting large data volumes. Selection sort is notable for its programming simplicity and it can over perform other sorts in certain situations\n" +
    "<br><br>\n" +
    "Bubble sort is a simple and well-known sorting algorithm. It is used in practice once in a blue moon and its main application is to make an introduction to the sorting algorithms. Bubble sort belongs to O(n2) sorting algorithms, which makes it quite inefficient for sorting large data volumes. Bubble sort is stable and adaptive.\n" +
    "</html>";


    private ArrayList<Cell> cells;
    private ArrayList<Double> data;
    private static int delayInMiliSeconds = 2000;
    private static ExecutorService executor = null;
    private SimulationThread simulationThread = null;
    
    public DAA() {
        initComponents();
        cells = new ArrayList<Cell>();
        data = new ArrayList<Double>();
        
        save.setVisible(false);
        
        panelBackground.setText(algorithm);
        notify.setText(algo);
        currentMinimum.setVisible(false);
        lastMinimum.setVisible(false);
        
        nextButon.setEnabled(false);
        resumeButton.setEnabled(false);
        pauseButton.setEnabled(false);
        cancelButton.setEnabled(false);
        simulate.setEnabled(false);
        steps.setEnabled(false);
        
        nextButon.setBackground(Color.decode("#bdc3c7"));
        resumeButton.setBackground(Color.decode("#bdc3c7"));
        pauseButton.setBackground(Color.decode("#bdc3c7"));
        cancelButton.setBackground(Color.decode("#bdc3c7"));
        simulate.setBackground(Color.decode("#bdc3c7"));
        steps.setBackground(Color.decode("#bdc3c7"));
        
        executor = Executors.newSingleThreadExecutor();

        secondsSlide.setValue(1);
        secondsSlide.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                int i =((JSlider) ce.getSource()).getValue();
                slider.setMinimum((i-1)*1000);
                slider.setMaximum((i+1)*1000);
                slider.setValue(i*1000);
            }
        });  
        
        int i =secondsSlide.getValue();
        slider.setMinimum((i-1)*1000);
        slider.setMaximum((i+1)*1000);
        slider.setValue(i*1000);
        slider.addChangeListener(new ChangeListener() {
        @Override
            public void stateChanged(ChangeEvent ce) {
                int i =((JSlider) ce.getSource()).getValue();
                delay.setText(String.valueOf(i));
                delayInMiliSeconds = i;
            }
        });

        comment.setText(intro);
        comment.setBounds(710, 10, 280, 730);
        comment.setVisible(true);

        panel.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                Dimension panelSize = panel.getSize();
                panelBackground.setSize(panelSize);
                panelBackground.setPreferredSize(panelSize);
                panelBackground.setMaximumSize(panelSize);
                panelBackground.setMinimumSize(panelSize);
                panelBackground.setLocation(0,0);
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        Dimension panelSize = new Dimension(700, 120);
        panel.setSize(panelSize);
        panel.setPreferredSize(panelSize);
        panel.setMaximumSize(panelSize);
        panel.setMinimumSize(panelSize);
        panel.setLocation(0,0);
    }
    
    private static int cellIndex = -1;
    private static Point cellPoint = new Point(10,10);
    
    private void addData(String word){
        Cell cell;
   
        String tempWord = word;
        cellIndex++;
        synchronized(this) {   
            try {
                data.add(cellIndex,Double.parseDouble(tempWord));

                cell = new Cell(data.get(cellIndex), cellIndex);

                cell.setLocation(cellPoint);

                cellPoint = new Point(cellPoint.x + 110, cellPoint.y);
                Dimension panelSize = new Dimension(100, 100);

                cell.setSize(panelSize);
                cell.setPreferredSize(panelSize);
                cell.setMaximumSize(panelSize);
                cell.setMinimumSize(panelSize);

                if(cellIndex>5){
                    Dimension newPanelSize = new Dimension(110*(cellIndex+1)+10, 120);
                    panel.setSize(newPanelSize);
                    panel.setPreferredSize(newPanelSize);
                    panel.setMaximumSize(newPanelSize);
                    panel.setMinimumSize(newPanelSize);
                    panel.setLocation(0,0);
                }

                panelBackground.setText("");
                notify.setText(""); 

                cells.add(cellIndex, cell);

                panel.add( cells.get(cellIndex));
                panel.setComponentZOrder( cells.get(cellIndex), cellIndex);
                //panel.revalidate();
                panel.repaint();
                cell = null;

                JOptionPane.showConfirmDialog(this,
                "Word " + tempWord + " successfully added to the index " + cellIndex,
                "Successfully Added", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
                simulate.setEnabled(true);
                steps.setEnabled(true);
                simulate.setBackground(new Color(44,62,80));
                steps.setBackground(new Color(44,62,80));


            } catch(NumberFormatException e) {  
                JOptionPane.showConfirmDialog(this,
                "Unrecognizable numaric value or format, try again with a valid numarical value",
                "Word not validated", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
                cellIndex--;
            }
        }
    }

    public void swap(final int fromIndex,final int toIndex) {
        synchronized(this)  {
            Double fromData = data.get(fromIndex);
            Double toData = data.get(toIndex);
            Double tempData = fromData;
            Cell fromCell = cells.get(fromIndex);
            Cell toCell = cells.get(toIndex);
            Cell tempCell = fromCell;

            String notice = "<html><b>\n" +
            "<font size=\"4\" color=\"#ffffff\">" +
            "Swapping<br>\n" +
            "<font size=\"4\" color=\""+hexColor(cells.get(fromIndex).getColor())+"\">" +
            "Value " + fromData + " At the index of " + fromIndex + "<br><br>\n" +
            "<font size=\"4\" color=\"#ffffff\">" +
            "With<br>\n" +
            "<font size=\"4\" color=\""+hexColor(cells.get(toIndex).getColor())+"\">" +
            "Value " + toData + " At the index of " + toIndex + "<br>\n" +
            "<html>";

            notify.setText(notice);
            notify.setVisible(true);

            Rectangle fromBounds = cells.get(fromIndex).getBounds();
            Rectangle toBounds = cells.get(toIndex).getBounds();

            Animate animateFrom = new Animate(cells.get(fromIndex),fromBounds, toBounds,delayInMiliSeconds);
            animateFrom.start();

            Animate animateTo = new Animate(cells.get(toIndex),toBounds, fromBounds,delayInMiliSeconds);
            animateTo.start();

            cells.get(fromIndex).setindex(String.valueOf(toIndex));
            cells.get(toIndex).setindex(String.valueOf(fromIndex));

            data.set(fromIndex, toData);
            data.set(toIndex, tempData);

            cells.set(fromIndex, toCell);
            cells.set(toIndex, tempCell);

            panel.repaint();
        }
    }
    
    public final static String hexColor(Color colour) {
        try {
            String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
            if (hexColour.length() < 6) {
                hexColour = hexColour + "000000".substring(0, 6 - hexColour.length());
            }
            return "#" + hexColour;
        } catch (Exception e) {}
        return null;
    }
    
    public void colorCell(int index, Color color){
        cells.get(index).setColor(color);
        cells.get(index).repaint();
    }
    
    public void showCurrentMinimum(int index){
        String s =(isAscendingOrder)?"Minimum":"Maximum";
        Double value = data.get(index);
        String text = "<html>\n" +
        "<font color=\"#ecf0f1\">\n" +
        "Current<br>\n" +
        s+"<br>\n" +
        "<font color=\"#ffffff\">\n" +
        value + "<br>\n" +
        "<br>\n" +
        "<font color=\"#ecf0f1\">\n" +
        "Index<br>\n" +
        "<font color=\"#ffffff\">\n" +
        index + "<br>\n" +
        "</html>";
        currentMinimum.setVisible(true);
        currentMinimum.setText(text);
        notify.setText(text);
        
        if(displayLastMinimumValue != null || displayLastMinimumIndex != null) {
            
            text = "<html>\n" +
            "<font color=\"#ecf0f1\">\n" +
            "Last<br>\n" +
            s+"<br>\n" +
            "<font color=\"#ffffff\">\n" +
            displayLastMinimumValue + "<br>\n" +
            "<br>\n" +
            "<font color=\"#ecf0f1\">\n" +
            "Index<br>\n" +
            "<font color=\"#ffffff\">\n" +
            displayLastMinimumIndex + "<br>\n" +
            "</html>";
            lastMinimum.setText(text);
        }
        lastMinimum.setVisible(true);
        displayLastMinimumValue = String.valueOf(value);
        displayLastMinimumIndex = String.valueOf(index);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        algorithmType = new javax.swing.ButtonGroup();
        sortingOrder = new javax.swing.ButtonGroup();
        comment = new javax.swing.JLabel();
        ascendingOrder = new javax.swing.JRadioButton();
        selectionSort = new javax.swing.JRadioButton();
        bubbleSort = new javax.swing.JRadioButton();
        descendingOrder = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        exit = new javax.swing.JButton();
        steps = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        simulate = new javax.swing.JButton();
        resumeButton = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        nextButon = new javax.swing.JButton();
        secondsSlide = new javax.swing.JSlider();
        slider = new javax.swing.JSlider();
        word = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        fileImport = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        geanarate = new javax.swing.JButton();
        save = new javax.swing.JButton();
        spinner = new javax.swing.JSpinner();
        panelScroll = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        panelBackground = new javax.swing.JLabel();
        lastMinimum = new javax.swing.JLabel();
        currentMinimum = new javax.swing.JLabel();
        notify = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        delay = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        center = new javax.swing.JLabel();
        right = new javax.swing.JLabel();
        left = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Algorithm Simulator");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(1000, 750));
        setMinimumSize(new java.awt.Dimension(1000, 750));
        setName("Algorithm Simulator"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 750));
        setResizable(false);
        getContentPane().setLayout(null);

        comment.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comment.setForeground(new java.awt.Color(255, 255, 255));
        comment.setText("<html>\nSummary -<br><br>\nFor each element in the list except last element<br>\nGet first element of the unsorted portion as the current minimum to compare<br>\nIterate over each element in the unsorted portion<br>\nCompare current minimum with the current unsorted element<br>\nIf the current unsorted element is the most suitable candidate remember it as the new current minimum<br>\nIf the first element of unsorted portion is not equal to the current minimum swap it<br>\n<br>\nBest case O(n² )<br>\nAverage case O(n² )<br>\nWorst case O(n² )<br>\n<br>\nEach iteration sort one element<br>\nAfter i iterations sorted portion must have i elements<br>\nAt the last iteration last element is already sorted<br>\n</html>");
        comment.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(comment);
        comment.setBounds(710, 10, 230, 730);

        ascendingOrder.setBackground(new java.awt.Color(26, 188, 156));
        sortingOrder.add(ascendingOrder);
        ascendingOrder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ascendingOrder.setForeground(new java.awt.Color(255, 255, 255));
        ascendingOrder.setSelected(true);
        ascendingOrder.setText("Ascending order");
        getContentPane().add(ascendingOrder);
        ascendingOrder.setBounds(480, 620, 180, 23);

        selectionSort.setBackground(new java.awt.Color(39, 174, 96));
        algorithmType.add(selectionSort);
        selectionSort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        selectionSort.setForeground(new java.awt.Color(255, 255, 255));
        selectionSort.setSelected(true);
        selectionSort.setText("Selection sort");
        getContentPane().add(selectionSort);
        selectionSort.setBounds(480, 540, 180, 23);

        bubbleSort.setBackground(new java.awt.Color(39, 174, 96));
        algorithmType.add(bubbleSort);
        bubbleSort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bubbleSort.setForeground(new java.awt.Color(255, 255, 255));
        bubbleSort.setText("Bubble sort");
        getContentPane().add(bubbleSort);
        bubbleSort.setBounds(480, 570, 180, 23);

        descendingOrder.setBackground(new java.awt.Color(26, 188, 156));
        sortingOrder.add(descendingOrder);
        descendingOrder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        descendingOrder.setForeground(new java.awt.Color(255, 255, 255));
        descendingOrder.setText("Descending order");
        getContentPane().add(descendingOrder);
        descendingOrder.setBounds(480, 650, 180, 23);

        jLabel3.setBackground(new java.awt.Color(39, 174, 96));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Algorithm");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3);
        jLabel3.setBounds(430, 520, 240, 80);

        jLabel4.setBackground(new java.awt.Color(26, 188, 156));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Sorting Order");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4);
        jLabel4.setBounds(430, 600, 240, 80);

        exit.setBackground(new java.awt.Color(22, 160, 133));
        exit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        exit.setForeground(new java.awt.Color(255, 255, 255));
        exit.setText("Exit");
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exit.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        exit.setDefaultCapable(false);
        exit.setDoubleBuffered(true);
        exit.setFocusPainted(false);
        exit.setOpaque(true);
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        getContentPane().add(exit);
        exit.setBounds(555, 450, 135, 40);

        steps.setBackground(new java.awt.Color(44, 62, 80));
        steps.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        steps.setForeground(new java.awt.Color(255, 255, 255));
        steps.setText("Step by Step");
        steps.setBorderPainted(false);
        steps.setContentAreaFilled(false);
        steps.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        steps.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        steps.setDefaultCapable(false);
        steps.setDoubleBuffered(true);
        steps.setFocusPainted(false);
        steps.setOpaque(true);
        steps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepsActionPerformed(evt);
            }
        });
        getContentPane().add(steps);
        steps.setBounds(210, 450, 190, 40);

        pauseButton.setBackground(new java.awt.Color(26, 188, 156));
        pauseButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pauseButton.setForeground(new java.awt.Color(255, 255, 255));
        pauseButton.setText("Pause");
        pauseButton.setBorderPainted(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pauseButton.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pauseButton.setDefaultCapable(false);
        pauseButton.setDoubleBuffered(true);
        pauseButton.setFocusPainted(false);
        pauseButton.setOpaque(true);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });
        getContentPane().add(pauseButton);
        pauseButton.setBounds(400, 80, 150, 40);

        cancelButton.setBackground(new java.awt.Color(26, 188, 156));
        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("27ae60");
        cancelButton.setBorderPainted(false);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cancelButton.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        cancelButton.setDefaultCapable(false);
        cancelButton.setDoubleBuffered(true);
        cancelButton.setFocusPainted(false);
        cancelButton.setOpaque(true);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        getContentPane().add(cancelButton);
        cancelButton.setBounds(550, 80, 140, 40);

        simulate.setBackground(new java.awt.Color(44, 62, 80));
        simulate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        simulate.setForeground(new java.awt.Color(255, 255, 255));
        simulate.setText("Simulate Algorithm");
        simulate.setBorderPainted(false);
        simulate.setContentAreaFilled(false);
        simulate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        simulate.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        simulate.setDefaultCapable(false);
        simulate.setDoubleBuffered(true);
        simulate.setFocusPainted(false);
        simulate.setOpaque(true);
        simulate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulateActionPerformed(evt);
            }
        });
        getContentPane().add(simulate);
        simulate.setBounds(10, 450, 190, 40);

        resumeButton.setBackground(new java.awt.Color(39, 174, 96));
        resumeButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        resumeButton.setForeground(new java.awt.Color(255, 255, 255));
        resumeButton.setText("Resume");
        resumeButton.setBorderPainted(false);
        resumeButton.setContentAreaFilled(false);
        resumeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resumeButton.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        resumeButton.setDefaultCapable(false);
        resumeButton.setDoubleBuffered(true);
        resumeButton.setFocusPainted(false);
        resumeButton.setOpaque(true);
        resumeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(resumeButton);
        resumeButton.setBounds(550, 20, 140, 40);

        clear.setBackground(new java.awt.Color(22, 160, 133));
        clear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        clear.setForeground(new java.awt.Color(255, 255, 255));
        clear.setText("Clear");
        clear.setToolTipText("");
        clear.setBorderPainted(false);
        clear.setContentAreaFilled(false);
        clear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        clear.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        clear.setDefaultCapable(false);
        clear.setDoubleBuffered(true);
        clear.setFocusPainted(false);
        clear.setOpaque(true);
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        getContentPane().add(clear);
        clear.setBounds(410, 450, 135, 40);

        nextButon.setBackground(new java.awt.Color(39, 174, 96));
        nextButon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nextButon.setForeground(new java.awt.Color(255, 255, 255));
        nextButon.setText("Next Step");
        nextButon.setBorderPainted(false);
        nextButon.setContentAreaFilled(false);
        nextButon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        nextButon.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        nextButon.setDefaultCapable(false);
        nextButon.setDoubleBuffered(true);
        nextButon.setFocusPainted(false);
        nextButon.setOpaque(true);
        nextButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButonActionPerformed(evt);
            }
        });
        getContentPane().add(nextButon);
        nextButon.setBounds(400, 20, 150, 40);

        secondsSlide.setBackground(new java.awt.Color(39, 174, 96));
        secondsSlide.setForeground(new java.awt.Color(44, 62, 80));
        secondsSlide.setMajorTickSpacing(1);
        secondsSlide.setMaximum(5);
        secondsSlide.setMinimum(1);
        secondsSlide.setMinorTickSpacing(1);
        secondsSlide.setPaintLabels(true);
        secondsSlide.setPaintTicks(true);
        secondsSlide.setSnapToTicks(true);
        getContentPane().add(secondsSlide);
        secondsSlide.setBounds(50, 540, 320, 40);

        slider.setBackground(new java.awt.Color(26, 188, 156));
        slider.setForeground(new java.awt.Color(44, 62, 80));
        slider.setMajorTickSpacing(400);
        slider.setMaximum(5);
        slider.setMinorTickSpacing(40);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        getContentPane().add(slider);
        slider.setBounds(50, 620, 320, 40);

        word.setForeground(new java.awt.Color(44, 62, 80));
        word.setText("Insert a word");
        word.setToolTipText("");
        word.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wordMouseClicked(evt);
            }
        });
        word.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wordActionPerformed(evt);
            }
        });
        getContentPane().add(word);
        word.setBounds(20, 30, 180, 30);

        add.setBackground(new java.awt.Color(44, 62, 80));
        add.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        add.setForeground(new java.awt.Color(255, 255, 255));
        add.setText("Add");
        add.setContentAreaFilled(false);
        add.setOpaque(true);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        getContentPane().add(add);
        add.setBounds(210, 30, 80, 30);

        fileImport.setBackground(new java.awt.Color(44, 62, 80));
        fileImport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fileImport.setForeground(new java.awt.Color(255, 255, 255));
        fileImport.setText("Import");
        fileImport.setContentAreaFilled(false);
        fileImport.setOpaque(true);
        fileImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileImportActionPerformed(evt);
            }
        });
        getContentPane().add(fileImport);
        fileImport.setBounds(300, 30, 80, 30);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Auto genarate words and insert number of rows");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(20, 70, 270, 30);

        geanarate.setBackground(new java.awt.Color(44, 62, 80));
        geanarate.setForeground(new java.awt.Color(255, 255, 255));
        geanarate.setText("Genarate");
        geanarate.setContentAreaFilled(false);
        geanarate.setOpaque(true);
        geanarate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geanarateActionPerformed(evt);
            }
        });
        getContentPane().add(geanarate);
        geanarate.setBounds(210, 100, 80, 30);

        save.setBackground(new java.awt.Color(44, 62, 80));
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setText("Save");
        save.setContentAreaFilled(false);
        save.setOpaque(true);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        getContentPane().add(save);
        save.setBounds(120, 100, 80, 30);

        spinner.setModel(new javax.swing.SpinnerNumberModel(10, 0, 10, 1));
        getContentPane().add(spinner);
        spinner.setBounds(300, 70, 80, 30);

        panelScroll.setBorder(null);
        panelScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panelScroll.setMaximumSize(new java.awt.Dimension(700, 140));
        panelScroll.setMinimumSize(new java.awt.Dimension(700, 140));
        panelScroll.setPreferredSize(new java.awt.Dimension(700, 140));

        panel.setMaximumSize(new java.awt.Dimension(1000, 120));
        panel.setMinimumSize(new java.awt.Dimension(1000, 120));
        panel.setPreferredSize(new java.awt.Dimension(1000, 120));
        panel.setLayout(null);

        panelBackground.setBackground(new java.awt.Color(41, 128, 185));
        panelBackground.setOpaque(true);
        panel.add(panelBackground);
        panelBackground.setBounds(169, 5, 150, 70);

        panelScroll.setViewportView(panel);

        getContentPane().add(panelScroll);
        panelScroll.setBounds(0, 150, 700, 140);

        lastMinimum.setBackground(new java.awt.Color(149, 165, 166));
        lastMinimum.setToolTipText("");
        lastMinimum.setOpaque(true);
        getContentPane().add(lastMinimum);
        lastMinimum.setBounds(480, 310, 100, 100);

        currentMinimum.setBackground(new java.awt.Color(127, 140, 141));
        currentMinimum.setToolTipText("");
        currentMinimum.setOpaque(true);
        getContentPane().add(currentMinimum);
        currentMinimum.setBounds(590, 310, 100, 100);

        notify.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(notify);
        notify.setBounds(10, 300, 460, 120);

        jLabel9.setBackground(new java.awt.Color(41, 128, 185));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Add Data");
        jLabel9.setToolTipText("");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185), 5));
        jLabel9.setOpaque(true);
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 10, 380, 130);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Simulation delay (miliseconds)");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 680, 180, 20);

        delay.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        delay.setForeground(new java.awt.Color(255, 255, 255));
        delay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        delay.setText("12345");
        getContentPane().add(delay);
        delay.setBounds(210, 680, 60, 20);

        jLabel5.setBackground(new java.awt.Color(39, 174, 96));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Seconds");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 520, 360, 70);

        jLabel6.setBackground(new java.awt.Color(26, 188, 156));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Miliseconds");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6);
        jLabel6.setBounds(30, 600, 360, 70);

        jLabel7.setBackground(new java.awt.Color(22, 160, 133));
        jLabel7.setOpaque(true);
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 500, 390, 210);

        jLabel2.setBackground(new java.awt.Color(22, 160, 133));
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(410, 500, 280, 210);

        center.setBackground(new java.awt.Color(41, 128, 185));
        center.setOpaque(true);
        getContentPane().add(center);
        center.setBounds(0, 430, 700, 320);

        right.setBackground(new java.awt.Color(44, 62, 80));
        right.setOpaque(true);
        getContentPane().add(right);
        right.setBounds(700, 0, 300, 750);

        left.setBackground(new java.awt.Color(52, 152, 219));
        left.setOpaque(true);
        getContentPane().add(left);
        left.setBounds(0, 0, 710, 430);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // TODO add your handling code here:
        addData(word.getText());
    }//GEN-LAST:event_addActionPerformed

    private void wordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wordActionPerformed
    private static boolean flag  = true;
    private void wordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wordMouseClicked
        // TODO add your handling code here:
        if(flag){
            word.setText("");
            flag = false;
        }
    }//GEN-LAST:event_wordMouseClicked

    private void geanarateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geanarateActionPerformed
        // TODO add your handling code here:
           double range;     
           int count = 0;
           try { 
               count = Integer.parseInt(spinner.getValue().toString());
               geanarate.setBackground(Color.decode("#bdc3c7"));
               geanarate.setEnabled(false);
           } catch (NumberFormatException e) {
                                JOptionPane.showConfirmDialog(this,
                 "Unrecognizable numaric value or format, try again with a valid number of word count",
                 "Word count not validated", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
           }
           for(int i = 0; i<count;i++){
               addData(String.valueOf(Math.random()*10).substring(0,5));
           }
           

            geanarate.setEnabled(true);
            geanarate.setBackground(new Color(44,62,80));
    }//GEN-LAST:event_geanarateActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
        save.setVisible(false);
        for(Cell c : cells) {
            panel.remove(c);
        }
        cellPoint = new Point(10,10);
        cellIndex = -1;
        
        cells = new ArrayList<Cell>();
        data = new ArrayList<Double>();
        
        Dimension panelSize = new Dimension(700, 120);
        panel.setSize(panelSize);
        panel.setPreferredSize(panelSize);
        panel.setMaximumSize(panelSize);
        panel.setMinimumSize(panelSize);
        panel.setLocation(0,0);
        word.setText("");
        displayLastMinimumValue = "";
        displayLastMinimumValue = "";
        comment.setText(intro);
        notify.setText("");
        panelBackground.setText(algorithm);
        notify.setText(algo);
        
        nextButon.setEnabled(false);
        resumeButton.setEnabled(false);
        pauseButton.setEnabled(false);
        cancelButton.setEnabled(false);
        simulate.setEnabled(false);
        steps.setEnabled(false);
        
        currentMinimum.setVisible(false);
        lastMinimum.setVisible(false);
        geanarate.setVisible(true);

        simulate.setBackground(Color.decode("#bdc3c7"));
        steps.setBackground(Color.decode("#bdc3c7"));
        nextButon.setBackground(Color.decode("#bdc3c7"));
        resumeButton.setBackground(Color.decode("#bdc3c7"));
        pauseButton.setBackground(Color.decode("#bdc3c7"));
        cancelButton.setBackground(Color.decode("#bdc3c7"));
    }//GEN-LAST:event_clearActionPerformed

    private void fileImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileImportActionPerformed
        final JFileChooser fileChooser = new JFileChooser(new File("Import data From"));
        ArrayList<Double> dataSet = new ArrayList<Double>();
        
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                final String name = f.getName();
                return name.endsWith(".csv") || name.endsWith(".xls") || name.endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Data Files *.csv, *.xls, *.txt";
            }
        });
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            ReadFromFile readFromFile = new ReadFromFile();
            readFromFile.setInputFile(this,fileChooser.getSelectedFile().getAbsolutePath());
            if(fileChooser.getSelectedFile().getName().endsWith(".xls")){
                System.out.println(fileChooser.getSelectedFile().getName());
                dataSet = readFromFile.readXLS();
            } else {
                String w = null;
                while(w == null || w.equals("")) {
                    w = JOptionPane.showInputDialog(this, "Insert a delimiter", "Delimiter not found", JOptionPane.QUESTION_MESSAGE);
                }
                dataSet = readFromFile.readANY(w);
            }   
        }
        for(Double wo :dataSet){
            addData(String.valueOf(wo));
       }
    }//GEN-LAST:event_fileImportActionPerformed

    private void nextButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButonActionPerformed
        // TODO add your handling code here:
        synchronized (this) {  

            nextButon.setEnabled(false);
            resumeButton.setEnabled(false);
            pauseButton.setEnabled(false);
            cancelButton.setEnabled(false);

            nextButon.setBackground(Color.decode("#bdc3c7"));
            resumeButton.setBackground(Color.decode("#bdc3c7"));
            pauseButton.setBackground(Color.decode("#bdc3c7"));
            cancelButton.setBackground(Color.decode("#bdc3c7"));

            Timer timer = new Timer(delayInMiliSeconds+1,new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    nextButon.setEnabled(true);
                    resumeButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    cancelButton.setEnabled(true);

                    nextButon.setBackground(new Color(39,174,96));
                    resumeButton.setBackground(new Color(39,174,96));
                    pauseButton.setBackground(Color.decode("#bdc3c7"));
                    cancelButton.setBackground(new Color(26,188,156));

                    ((Timer)actionEvent.getSource()).stop();
                }
            });
            timer.start();
        
            simulationThread.userNextStep();
        }
    }//GEN-LAST:event_nextButonActionPerformed

    private void resumeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumeButtonActionPerformed
        // TODO add your handling code here:       
        synchronized (this) {
           // delayUI();
        
            nextButon.setEnabled(false);
            resumeButton.setEnabled(false);
            pauseButton.setEnabled(true);
            cancelButton.setEnabled(true);

            nextButon.setBackground(Color.decode("#bdc3c7"));
            resumeButton.setBackground(Color.decode("#bdc3c7"));
            pauseButton.setBackground(new Color(26,188,156));
            cancelButton.setBackground(new Color(26,188,156));
        
            simulationThread.userResume();
        }
    }//GEN-LAST:event_resumeButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        // TODO add your handling code here:
        synchronized (this) {
           // delayUI();
            
            nextButon.setEnabled(true);
            resumeButton.setEnabled(true);
            pauseButton.setEnabled(false);
            cancelButton.setEnabled(true);

            nextButon.setBackground(new Color(39,174,96));
            resumeButton.setBackground(new Color(39,174,96));
            pauseButton.setBackground(Color.decode("#bdc3c7"));
            cancelButton.setBackground(new Color(26,188,156));
        
            simulationThread.userPause();
        }
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void stepsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepsActionPerformed
        // TODO add your handling code here:      
        save.setVisible(false);
        boolean validated = true;  
        geanarate.setVisible(false);
        simulate.setEnabled(false);
        steps.setEnabled(false);

        simulate.setBackground(Color.decode("#bdc3c7"));
        steps.setBackground(Color.decode("#bdc3c7"));

        if(validated){

            isAscendingOrder = ascendingOrder.isSelected();
            isSelectionSort = selectionSort.isSelected();

            if (isAscendingOrder) {
                displayOrder = "Left To Right Ascending Order";
                displayCurrentValue = "Current Minimum Value";
            } else {
                displayOrder = "Left To Right Descending Order";
                displayCurrentValue = "Current Maximum Value";
            }

            if(isSelectionSort){
                displayAlgorithm = "Selection Sort Algorithm";
                comment.setText(selectionSortDescription);
            }else{
                displayAlgorithm = "Bubble Sort Algorithm";
                displayCurrentValue = "Swapping With Value";
                comment.setText(bubbleSortDescription);
            }

            simulationThread = new SimulationThread(isSelectionSort,isAscendingOrder);
            executor = Executors.newSingleThreadExecutor();
            executor.execute(simulationThread);
        
            simulationThread.userPause();
            
            nextButon.setEnabled(true);
            resumeButton.setEnabled(true);
            cancelButton.setEnabled(true);

            nextButon.setBackground(new Color(39,174,96));
            resumeButton.setBackground(new Color(39,174,96));
            cancelButton.setBackground(new Color(26,188,156));

            pauseButton.setEnabled(false);
            pauseButton.setBackground(Color.decode("#bdc3c7"));
            delayUI();
        }
    }//GEN-LAST:event_stepsActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        displayStatus = "Simulation Cancelled";
        
        synchronized (this) {    

            nextButon.setEnabled(false);
            resumeButton.setEnabled(false);
            pauseButton.setEnabled(false);
            cancelButton.setEnabled(false);

            nextButon.setBackground(Color.decode("#bdc3c7"));
            resumeButton.setBackground(Color.decode("#bdc3c7"));
            pauseButton.setBackground(Color.decode("#bdc3c7"));
            cancelButton.setBackground(Color.decode("#bdc3c7"));
            
            simulate.setEnabled(false);
            steps.setEnabled(false);
            simulate.setBackground(new Color(44,62,80));
            steps.setBackground(new Color(44,62,80));
        
            simulationThread.terminate();
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void simulateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateActionPerformed
        // TODO add your handling code here:
        displayStatus = "Simulation Started";
        save.setVisible(false);

        geanarate.setVisible(false);
        simulate.setEnabled(false);
        steps.setEnabled(false);

        simulate.setBackground(Color.decode("#bdc3c7"));
        steps.setBackground(Color.decode("#bdc3c7"));


            isAscendingOrder = ascendingOrder.isSelected();
            isSelectionSort = selectionSort.isSelected();

            if (isAscendingOrder) {
                displayOrder = "Left To Right Ascending Order";
                displayCurrentValue = "Current Minimum Value";
            } else {
                displayOrder = "Left To Right Descending Order";
                displayCurrentValue = "Current Maximum Value";
            }

            if(isSelectionSort){
                displayAlgorithm = "Selection Sort Algorithm";
                comment.setText(selectionSortDescription);
            }else{
                displayAlgorithm = "Bubble Sort Algorithm";
                displayCurrentValue = "Swapping With Value";
                comment.setText(bubbleSortDescription);
            }

            simulationThread = new SimulationThread(isSelectionSort,isAscendingOrder);
            executor = Executors.newSingleThreadExecutor();
            executor.execute(simulationThread);

            nextButon.setEnabled(true);
            resumeButton.setEnabled(true);
            cancelButton.setEnabled(true);

            nextButon.setBackground(new Color(39,174,96));
            resumeButton.setBackground(new Color(39,174,96));
            cancelButton.setBackground(new Color(26,188,156));

            resumeButton.setEnabled(false);
            resumeButton.setBackground(Color.decode("#bdc3c7"));
            nextButon.setEnabled(false);
            nextButon.setBackground(Color.decode("#bdc3c7"));
            delayUI();
    }//GEN-LAST:event_simulateActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file name to save");   

        int userSelection = fileChooser.showSaveDialog(this);
        String filePath ="";
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            filePath = fileToSave.getAbsolutePath();
            try {
            filePath = filePath.substring(0,filePath.indexOf('.'))+".txt";
            } catch (Exception e){}
            writeToFile(filePath, false);
        }
    }//GEN-LAST:event_saveActionPerformed
    
    public void writeToFile(String filePath, boolean append) {  
        BufferedWriter writer = null;  
        try  
        {  
            String lineSep = System.getProperty("line.separator");  
            try{
                writer = new BufferedWriter(new FileWriter(filePath, append));  

                for (Double d : data) {
                    writer.write(String.valueOf(d) + lineSep);
                } 
            }catch (IOException e){
            }finally{
                if (writer != null){  
                    writer.close();  
                }
            }
        } catch (IOException e2) {}
    }
    
    class SimulationThread extends Thread {
        private final boolean isSelectionSort;
        private final boolean isAscendingOrder;
        private boolean loop = false;
        private boolean wait = false;
        private boolean resume = false;
        private boolean run = true;
        

     
        public SimulationThread(boolean isSelectionSort,boolean isAscendingOrder){
            this.isAscendingOrder = isAscendingOrder;
            this.isSelectionSort = isSelectionSort;
        }

        @Override
        public void run() {
            if(this.isSelectionSort){
                selectionSort();
            } else {
                bubbleSort();
            }
            nextButon.setEnabled(false);
            resumeButton.setEnabled(false);
            pauseButton.setEnabled(false);
            cancelButton.setEnabled(false);
            simulate.setEnabled(false);
            steps.setEnabled(false);

            simulate.setBackground(Color.decode("#bdc3c7"));
            steps.setBackground(Color.decode("#bdc3c7"));
            nextButon.setBackground(Color.decode("#bdc3c7"));
            resumeButton.setBackground(Color.decode("#bdc3c7"));
            pauseButton.setBackground(Color.decode("#bdc3c7"));
            cancelButton.setBackground(Color.decode("#bdc3c7"));
            save.setVisible(true);
            panel.repaint();
        }

        public void getInfo(int minimumIndex, int currentIndex){
            String displayCurrentVal = String.valueOf(data.get(minimumIndex));
            String displayNewValue = String.valueOf(data.get(currentIndex));
            String displayNewIndex = String.valueOf(currentIndex);
            
            String t = "<html>\n" +
            "<font color=\"#ffffff\">\n" +
            "Sorting On " + displayOrder+"<br>\n" +
            "Using " + displayAlgorithm+"<br>\n" +     
            displayCurrentValue + " " + displayCurrentVal +"<br>"+
            "Checking On "+displayNewValue+" At The Index Of "+displayNewIndex+
            "</html>";       

            notify.setText(t);
        }

        public void selectionSort(){

            Tn :  while (true) {
                displayStatus = "Sorting Data";
                int arraySize = data.size();

                for(int x=0; x < arraySize; x++){

                    int minimum = x;
                    showCurrentMinimum(x);
                   
                    colorCell(minimum,from);

                    for(int y=x; y < arraySize; y++){
                         
                        getInfo(minimum, y);
                        colorCell(y,to);
                        pause();

                        if(isAscendingOrder){
                            if(data.get(minimum)>data.get(y)){
                                minimum = y;
                                showCurrentMinimum(y);
                                colorCell(y,temp);
                                
                                pause();
                                if(!run){
                                break Tn;
                                }
                            }

                        }else{
                            if(data.get(minimum)<data.get(y)){
                                minimum = y;                       

                                showCurrentMinimum(y);
                                colorCell(y,temp);
                                pause();
                                if(!run){
                                break Tn;
                                }
                            }
                        }
                        
                        if(!run){
                            break Tn;
                        }

                        colorCell(y,unsorted);

                    }
  
                    colorCell(minimum,sorted);
                    pause();
                    if(x != minimum) {
                        swap(x, minimum);
                    }
                    pause();
                     if(!run){
                       break Tn;
                    }
                    
                }
                break;
            }
            
            //for(Double d : data){
            //    System.out.println(d);
            //}
        }
        
        public void bubbleSort(){
            Tn :  while (true) {
                displayStatus = "Sorting Data";
                int arraySize = data.size();

                for(int i = arraySize - 1; i >= 0; i--){

                    for(int j = 0; j < i; j++){
                            notifyMe(j, j+1);
                            colorCell(j,from);
                            colorCell(j+1,to);
                            pause();

                        if(isAscendingOrder) {
                            if(data.get(j) > data.get(j+1)){		
                                swap(j, j+1);
                                pause();
                                if(!run){
                                   break Tn;
                                }
                            }
                        } else {
                            if(data.get(j) < data.get(j+1)){
                                swap(j, j+1);
                                pause();
                                if(!run){
                                   break Tn;
                                }
                            }
                        }
                        colorCell(j,unsorted);
                        colorCell(j+1,unsorted);
                        pause();
                    }
                    colorCell(i,sorted);
                }  
                break;
            }
            
            for(Double d : data){
                System.out.println(d);
            }
        }
              
        public void notifyMe(int indexFrom, int indexTo){
            String displayCurrentVal = String.valueOf(data.get(indexFrom));
            String displayNewValue = String.valueOf(data.get(indexTo));
            String displayCurrentIdx = String.valueOf(indexFrom);
            String displayNewIndex = String.valueOf(indexTo);
            
            String t = "<html>\n" +
            "<font color=\"#ffffff\">\n" +
            "Sorting On " + displayOrder+"<br>\n" +
            "Using " + displayAlgorithm+"<br>\n" +     
            displayCurrentValue + " " + displayCurrentVal +" At The Index Of "+displayCurrentIdx+"<br>"+
            "To the value "+displayNewValue+" At The Index Of "+displayNewIndex+"<br>"+
            "Sorted portion begins at the end of the Array<br>" +
            "Each iteration adds a sorted value to the sorted portion <br>"+
            "As the first index of the sorted portion decrements<br>" +
            "</html>";       

            notify.setText(t);
            notify.setVisible(true);
        }
  
        private void pause() {
            try {
                SimulationThread.sleep(delayInMiliSeconds);
                synchronized (this) {
                    while (loop) {
                        wait();
                        
                        if (wait) {
                            
                            if (resume) {
                                loop = false;
                            }
                            break;
                        }   
                    }
                }
            } catch (InterruptedException e) {}
        }

        public synchronized void terminate() {
            wait = true;
            resume = false;
            run = false;
            notify();   
        }
    
    
        public synchronized void userPause() {
            loop = true;
            wait = false;
            resume = false;
            notify();
        }
   
        public synchronized void userNextStep() {
            loop = true;
            wait = true;
            resume = false;
            notify();
        }
      
        public synchronized void userResume() {
            loop = true;
            wait = true;
            resume = true;
            notify();
        }
    }
    
    public void delayUI(){

        nextButon.setEnabled(false);
        resumeButton.setEnabled(false);
        pauseButton.setEnabled(false);
        cancelButton.setEnabled(false);
        simulate.setEnabled(false);
        steps.setEnabled(false);
        clear.setEnabled(false);
        exit.setEnabled(false);

        nextButon.setBackground(Color.decode("#bdc3c7"));
        resumeButton.setBackground(Color.decode("#bdc3c7"));
        pauseButton.setBackground(Color.decode("#bdc3c7"));
        cancelButton.setBackground(Color.decode("#bdc3c7"));
        simulate.setBackground(Color.decode("#bdc3c7"));
        steps.setBackground(Color.decode("#bdc3c7"));
        clear.setBackground(Color.decode("#bdc3c7"));
        exit.setBackground(Color.decode("#bdc3c7"));
            
        Timer timer = new Timer(delayInMiliSeconds+1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            
                nextButon.setEnabled(true);
                resumeButton.setEnabled(true);
                pauseButton.setEnabled(true);
                cancelButton.setEnabled(true);
                simulate.setEnabled(true);
                steps.setEnabled(true);
                clear.setEnabled(true);
                exit.setEnabled(true);
                
                nextButon.setBackground(new Color(39,174,96));
                resumeButton.setBackground(new Color(39,174,96));
                pauseButton.setBackground(new Color(26,188,156));
                cancelButton.setBackground(new Color(26,188,156));
                simulate.setBackground(new Color(44,62,80));
                steps.setBackground(new Color(44,62,80));
                clear.setBackground(new Color(22,160,133));
                exit.setBackground(new Color(22,160,133));

                ((Timer)actionEvent.getSource()).stop();
            }
        });
        timer.start();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DAA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DAA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DAA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DAA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DAA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.ButtonGroup algorithmType;
    private javax.swing.JRadioButton ascendingOrder;
    private javax.swing.JRadioButton bubbleSort;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel center;
    private javax.swing.JButton clear;
    private javax.swing.JLabel comment;
    private javax.swing.JLabel currentMinimum;
    private javax.swing.JLabel delay;
    private javax.swing.JRadioButton descendingOrder;
    private javax.swing.JButton exit;
    private javax.swing.JButton fileImport;
    private javax.swing.JButton geanarate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lastMinimum;
    private javax.swing.JLabel left;
    private javax.swing.JButton nextButon;
    private javax.swing.JLabel notify;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel panelBackground;
    private javax.swing.JScrollPane panelScroll;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton resumeButton;
    private javax.swing.JLabel right;
    private javax.swing.JButton save;
    private javax.swing.JSlider secondsSlide;
    private javax.swing.JRadioButton selectionSort;
    private javax.swing.JButton simulate;
    private javax.swing.JSlider slider;
    private javax.swing.ButtonGroup sortingOrder;
    private javax.swing.JSpinner spinner;
    private javax.swing.JButton steps;
    private javax.swing.JTextField word;
    // End of variables declaration//GEN-END:variables
}
