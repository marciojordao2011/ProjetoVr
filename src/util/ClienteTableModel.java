package util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entities.ClienteVenda;

public class ClienteTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private List<ClienteVenda> clientes;
    private String[] colunas = {"ID", "Nome"};

    public ClienteTableModel(List<ClienteVenda> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClienteVenda cliente = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cliente.getId();
            case 1:
                return cliente.getNome();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}
