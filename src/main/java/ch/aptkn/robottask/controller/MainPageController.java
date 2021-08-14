package ch.aptkn.robottask.controller;

import ch.aptkn.robottask.command.IScriptCommand;
import ch.aptkn.robottask.exception.ScriptCommandExecutionException;
import ch.aptkn.robottask.exception.ScriptParserException;
import ch.aptkn.robottask.model.Grid;
import ch.aptkn.robottask.model.InputScript;
import ch.aptkn.robottask.model.Robot;
import ch.aptkn.robottask.service.ScriptExecutorService;
import ch.aptkn.robottask.service.ScriptParserService;
import com.sun.tools.javac.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainPageController {
    private static final int GRID_SIZE = 5;
    private final ScriptParserService scriptParserService;
    private final ScriptExecutorService scriptExecutorService;

    public MainPageController(ScriptParserService scriptParserService, ScriptExecutorService scriptExecutorService) {
        this.scriptParserService = scriptParserService;
        this.scriptExecutorService = scriptExecutorService;
    }

    @GetMapping("/")
    public String mainPageForm(Model model) {
        model.addAttribute("inputScript", new InputScript());
        return "main-form";
    }

    @PostMapping("/")
    public String mainPageSubmit(@ModelAttribute InputScript inputScript, Model model) {
        model.addAttribute("inputScript", inputScript);
        Grid grid = new Grid(GRID_SIZE, GRID_SIZE);
        model.addAttribute("grid", grid);
        try {
            List<IScriptCommand> commands = scriptParserService.parseScript(inputScript.getSource());
            Robot robot = scriptExecutorService.executeScript(grid, commands);
            model.addAttribute("robot", robot);
        } catch (ScriptParserException | ScriptCommandExecutionException e) {
            model.addAttribute("error", e);
        }
        return "main-result";
    }
}
