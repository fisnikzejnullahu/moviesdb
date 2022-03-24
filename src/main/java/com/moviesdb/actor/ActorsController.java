package com.moviesdb.actor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/actors")
public class ActorsController {

    private final ActorsService actorsService;

    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(name = "sort_by", defaultValue = "moviesCount") String sortBy,
                        @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
                        Model model) {
        System.out.println("sortBy = " + sortBy);
        System.out.println("sortOrder = " + sortOrder);

        model.addAttribute("model", actorsService.getActors(page, sortBy, sortOrder));
        return "actors/index";
    }

    @GetMapping("{id}")
    public String details(@PathVariable("id") long id, Model model) {
        ActorDetailsModel actorModel = actorsService.getActor(id);
        if (actorModel == null) {
            return "404";
        }
        model.addAttribute("model", actorModel);
        return "actors/details";
    }
}
