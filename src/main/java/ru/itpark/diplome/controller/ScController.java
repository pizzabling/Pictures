package ru.itpark.diplome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.diplome.domain.Picture;
import ru.itpark.diplome.domain.User;
import ru.itpark.diplome.repository.ScRepository;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Controller
public class ScController {
    private final ScRepository scRepository;
    @Value("${upload.path}")
    private String uploadPath;

    public ScController(ScRepository scRepository) {
        this.scRepository = scRepository;
    }

    @GetMapping("/")
    public String home(Model model) {

        return "home";
    }

    @GetMapping("/user-pictures/{user}")
    public String userPictures(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Picture picture
    ) {
        Set<Picture> pictures = user.getPictures();

        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("pictures", pictures);
        model.addAttribute("picture", picture);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userPictures";
    }
    @PostMapping("/user-pictures/{user}")
    public String updatePicture(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Picture picture,
            @RequestParam("text") String text,
            @RequestParam("tags") String tags,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (picture.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                picture.setText(text);
            }

            if (!StringUtils.isEmpty(tags)) {
                picture.setTags(tags);
            }



            scRepository.save(picture);
        }

        return "redirect:/user-pictures/" + user;
    }


    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Picture> pictures = scRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            pictures = scRepository.findByTags(filter);
        } else {
            pictures = scRepository.findAll();

        }

        model.addAttribute("pictures", pictures);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(Model model,
                      @AuthenticationPrincipal User user,
                      @RequestParam String picturename,
                      @RequestParam String creator,
                      @RequestParam String text,
                      @RequestParam String tags,
                      @RequestParam("file") MultipartFile file) throws IOException {
        Picture picture = new Picture(picturename,creator,text,tags, user);
        if (file != null&&!file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            picture.setFilename(resultFilename);
        }

        scRepository.save(picture);
        Iterable<Picture> pictures = scRepository.findAll();
        model.addAttribute("pictures", pictures);
        return "main";
    }
}
