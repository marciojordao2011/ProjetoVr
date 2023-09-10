package util;

import javax.swing.*;

import entities.ClienteVenda;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroCliente {
    private JFrame frame;
    private JTextField nomeTextField;

    public TelaCadastroCliente() {
        frame = new JFrame("Cadastro de Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel nomeLabel = new JLabel("Nome do Cliente:");
        nomeTextField = new JTextField();

        JButton cadastrarButton = new JButton("Cadastrar");
        JButton cancelarButton = new JButton("Cancelar");

        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(cadastrarButton);
        panel.add(cancelarButton);

        frame.add(panel);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText().trim();
                if (!nome.isEmpty()) {
                    
                    ClienteVenda novoCliente = new ClienteVenda();
                    novoCliente.setNome(nome);

                    

                    JOptionPane.showMessageDialog(frame, "Cliente cadastrado com sucesso!");
                    frame.dispose(); 
                } else {
                    JOptionPane.showMessageDialog(frame, "O nome do cliente não pode estar vazio.");
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
            }
        });

        frame.setVisible(true);
    }
}
