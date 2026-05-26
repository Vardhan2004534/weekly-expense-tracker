import javax.swing.*;
import java.awt.*;


public class WeeklyExpenseTracker extends JFrame {

    private JComboBox<String> dayBox;
    private JComboBox<String> categoryBox;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTextArea outputArea;

    private ArrayList<Expense> expenses = new ArrayList<>();


    public WeeklyExpenseTracker() {

        setTitle("Weekly Expense Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Day"));
        dayBox = new JComboBox<>(new String[]{
                "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday"
        });
        panel.add(dayBox);

        panel.add(new JLabel("Category"));
        categoryBox = new JComboBox<>(new String[]{
                "Groceries", "Eating Out", "Petrol",
                "Taxi", "Bills", "Rent", "Others"
        });
        panel.add(categoryBox);

        panel.add(new JLabel("Amount"));
        amountField = new JTextField();
        panel.add(amountField);

        panel.add(new JLabel("Description"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        JButton addBtn = new JButton("Add Expense");
        JButton totalBtn = new JButton("Weekly Total");

        panel.add(addBtn);
        panel.add(totalBtn);

        add(panel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JButton summaryBtn = new JButton("Category Summary");
        add(summaryBtn, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addExpense());
        totalBtn.addActionListener(e -> showTotal());
        summaryBtn.addActionListener(e -> showSummary());

       
    }

      private void addExpense() {

        try {
            String day = (String) dayBox.getSelectedItem();
            String category = (String) categoryBox.getSelectedItem();

            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Amount must be greater than 0");
                return;
            }

            String desc = descriptionField.getText();

            expenses.add(new Expense(day, category, amount, desc));

            outputArea.append("Added -> " + day + " | "
                    + category + " | $" + amount + " | "
                    + desc + "\n");

            amountField.setText("");
            descriptionField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter valid amount");
        }
    }

      private void showTotal() {

        double total = 0;

        for (Expense e : expenses) {
            total += e.amount;
        }

        outputArea.append("\nTotal Weekly Expense = $"
                + total + "\n\n");
    }

    private void showSummary() {

        HashMap<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {
            map.put(
                    e.category,
                    map.getOrDefault(e.category, 0.0)
                            + e.amount
            );
        }

        outputArea.append("\nCategory Summary\n");

        for (String key : map.keySet()) {
            outputArea.append(
                    key + " : $" + map.get(key) + "\n"
            );
        }

        outputArea.append("\n");
    }

public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new WeeklyExpenseTracker().setVisible(true);
        });
    }
}


class Expense {

    String day;
    String category;
    double amount;
    String description;

    Expense(String day,
            String category,
            double amount,
            String description) {

        this.day = day;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }
}

