package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import org.json.JSONArray;
import org.json.JSONObject;

public class SpajanjeAPI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblItemName;
    private JLabel lblItemPrice;
    private JLabel lblLoading;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SpajanjeAPI frame = new SpajanjeAPI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SpajanjeAPI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(128, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Spajanje na API");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(125, 24, 154, 21);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("API");
        btnNewButton.setBackground(new Color(128, 255, 0));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNewButton.setBounds(142, 105, 120, 48);
        contentPane.add(btnNewButton);

        lblItemName = new JLabel("");
        lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblItemName.setBounds(50, 180, 350, 20);
        contentPane.add(lblItemName);

        lblItemPrice = new JLabel("");
        lblItemPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblItemPrice.setBounds(50, 210, 350, 20);
        contentPane.add(lblItemPrice);

        lblLoading = new JLabel("Učitavam podatke, molim pričekajte...");
        lblLoading.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblLoading.setBounds(100, 180, 250, 20);
        lblLoading.setVisible(false); // Initially hidden
        contentPane.add(lblLoading);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spojiNaApi();
            }
        });
    }

    private void spojiNaApi() {
        // Show loading message
        lblLoading.setVisible(true);
        lblItemName.setText("");
        lblItemPrice.setText("");

        String apiUrl = "http://localhost:3000/api/products";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    parseAndDisplayResponse(response);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                })
                .thenRun(() -> {
                    // Hide loading message after response handling completes
                    lblLoading.setVisible(false);
                });
    }

    private void parseAndDisplayResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length() > 0) {
                JSONObject firstItem = jsonArray.getJSONObject(0);
                String name = firstItem.getString("name");
                String price = firstItem.getString("price");

                // Update UI components on the Event Dispatch Thread
                EventQueue.invokeLater(() -> {
                    lblItemName.setText("Name: " + name);
                    lblItemPrice.setText("Price: " + price);
                });
            } else {
                // Handle empty response
                EventQueue.invokeLater(() -> {
                    lblItemName.setText("No items found.");
                    lblItemPrice.setText("");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            EventQueue.invokeLater(() -> {
                lblItemName.setText("Error parsing response.");
                lblItemPrice.setText("");
            });
        }
    }
}
