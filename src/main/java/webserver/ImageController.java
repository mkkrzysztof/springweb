package webserver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class ImageController {

    private static final String URL = "jdbc:sqlite:/home/krzysztof/IdeaProjects/ClientServerPowtorka/diodes.db";
    //http://localhost:8912/image?username=sus&electrode=6
    @GetMapping("/image")
    public String image(@RequestParam String username, @RequestParam int electrode, Model model) {
        String image= null;
        String sql = "SELECT image FROM user_eeg WHERE username = ? AND electrode_number = ?";
        if(electrode > 18){
            model.addAttribute("username", "Skill");
            model.addAttribute("electrode", "Issue");
            String line = null;
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader("xd.txt"))){
                line = bufferedReader.readLine();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            model.addAttribute("image", line);
            return "eegimage";
        }

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, electrode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    image = rs.getString("image");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

        model.addAttribute("username", username);
        model.addAttribute("electrode", electrode);
        model.addAttribute("image", image);
        return "eegimage";
    }
}