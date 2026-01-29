package ui;

import model.Resource;
import model.ResourceType;
import service.ResourceService;

import javax.swing.*;
import java.awt.*;

public class ResourceManagerDialog extends JDialog {
    private final ResourceService resourceService;
    private final Resource resource; // may be null for add

    private JTextField idField;
    private JTextField nameField;
    private JComboBox<ResourceType> typeCombo;
    private JSpinner capacitySpinner;
    private JTextField locationField;
    private JCheckBox availableCheck;

    public ResourceManagerDialog(JFrame parent, ResourceService resourceService, Resource resource) {
        super(parent, true);
        this.resourceService = resourceService;
        this.resource = resource;
        setTitle(resource == null ? "Add Resource" : "Edit Resource");
        initUI();
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        idField = new JTextField();
        nameField = new JTextField();
        typeCombo = new JComboBox<>(ResourceType.values());
        capacitySpinner = new JSpinner(new SpinnerNumberModel(30, 1, 1000, 1));
        locationField = new JTextField();
        availableCheck = new JCheckBox("Available");

        form.add(new JLabel("Resource ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Type:")); form.add(typeCombo);
        form.add(new JLabel("Capacity:")); form.add(capacitySpinner);
        form.add(new JLabel("Location:")); form.add(locationField);
        form.add(new JLabel("")); form.add(availableCheck);

        add(form, BorderLayout.CENTER);

        if (resource != null) {
            idField.setText(resource.getResourceId());
            idField.setEnabled(false);
            nameField.setText(resource.getName());
            typeCombo.setSelectedItem(resource.getType());
            capacitySpinner.setValue(resource.getCapacity());
            locationField.setText(resource.getLocation());
            availableCheck.setSelected(resource.isAvailable());
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> dispose());
        buttons.add(save);
        buttons.add(cancel);
        add(buttons, BorderLayout.SOUTH);
    }

    private void onSave() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Resource ID required");
            return;
        }
        Resource r = resource == null ? new Resource() : resource;
        r.setResourceId(id);
        r.setName(nameField.getText().trim());
        r.setType((ResourceType) typeCombo.getSelectedItem());
        r.setCapacity((Integer) capacitySpinner.getValue());
        r.setLocation(locationField.getText().trim());
        r.setAvailable(availableCheck.isSelected());

        resourceService.addResource(resource);

        dispose();
    }
}
