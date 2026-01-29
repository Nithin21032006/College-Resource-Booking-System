import service.AuthService;
import service.ResourceService;
import service.BookingService;
import model.Resource;
import model.ResourceType;
import ui.LoginFrame;

import javax.swing.*;

public class MainApplication {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize services
        AuthService authService = new AuthService();
        ResourceService resourceService = new ResourceService();
        BookingService bookingService = new BookingService();

        // Preload resources
        resourceService.addResource(new Resource("R001", "Conference Room A", ResourceType.ROOM, 10, "1st Floor"));
        resourceService.addResource(new Resource("R002", "Conference Room B", ResourceType.ROOM, 20, "2nd Floor"));
        resourceService.addResource(new Resource("R003", "Projector X100", ResourceType.EQUIPMENT, 1, "Storage Room"));
        resourceService.addResource(new Resource("R004", "Laptop L200", ResourceType.EQUIPMENT, 1, "Storage Room"));

        // Start login frame on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginFrame(authService, resourceService, bookingService));
    }
}
