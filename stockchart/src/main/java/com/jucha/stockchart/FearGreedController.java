package com.jucha.stockchart;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class FearGreedController {

    private final FearGreedService service;

    public FearGreedController(FearGreedService service) {
        this.service = service;
    }

    @GetMapping("/feargreed")
    public String fearGreedPage(Model model) {
        List<FearGreedData> data = service.getAll();
        model.addAttribute("data", data);
        model.addAttribute("latest", service.getLatest());
        return "feargreed";  // -> templates/feargreed.html
    }

    @GetMapping("/api/feargreed")
    @ResponseBody
    public List<FearGreedData> getFearGreedData() {
        return service.getAll();
    }
}
