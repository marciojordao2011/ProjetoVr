package util;

import javax.swing.*;

import entities.ClienteVenda;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelecionarClienteDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private JButton selecionarButton;
    private ClienteVenda clienteSelecionado;

    public SelecionarClienteDialog(JFrame parent, List<ClienteVenda> clientes) {
        super(parent, "Selecionar Cliente", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);

        table = new JTable(new ClienteTableModel(clientes));

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        selecionarButton = new JButton("Selecionar");

        selecionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    clienteSelecionado = clientes.get(selectedRow);
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(parent, "Selecione um cliente primeiro.");
                }
            }
        });

        buttonPanel.add(selecionarButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public ClienteVenda getClienteSelecionado() {
        return clienteSelecionado;
    }
}