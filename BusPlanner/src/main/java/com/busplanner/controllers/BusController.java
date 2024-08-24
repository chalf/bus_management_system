
import com.busplanner.pojo.Buses;
import com.busplanner.services.BusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping("/buses")
public class BusController {
    @Autowired
    private BusService busService;

    @GetMapping
    public String listBuses(Model model) {
        List<Buses> buses = busService.getListBus();
        model.addAttribute("buses", buses);
        return "busPage";  // Ensure this matches the name defined in tiles.xml
    }
}
