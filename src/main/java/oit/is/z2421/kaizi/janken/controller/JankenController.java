package oit.is.z2421.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  @GetMapping("/janken")
  public String janken(@RequestParam(required = false) String name, @RequestParam(required = false) String hand, Model model) {
    String cpuHand = "rock";
    String result = "";

    if (name != null && !name.isEmpty()) {
      model.addAttribute("username", name);
    }

    if (hand != null) {
      if (hand.equals("rock")) {
        result = cpuHand.equals("rock") ? "引き分け" : (cpuHand.equals("scissors") ? "勝ち" : "負け");
      } else if (hand.equals("scissors")) {
        result = cpuHand.equals("rock") ? "負け" : (cpuHand.equals("scissors") ? "引き分け" : "勝ち");
      } else if (hand.equals("paper")) {
        result = cpuHand.equals("rock") ? "勝ち" : (cpuHand.equals("scissors") ? "負け" : "引き分け");
      }
    }

    model.addAttribute("result", result);
    model.addAttribute("userHand", hand);
    model.addAttribute("cpuHand", cpuHand);

    return "janken";
  }

  @GetMapping("/janken.html")
  public String jankenHtml(Model model) {
    return "janken";
  }
}
