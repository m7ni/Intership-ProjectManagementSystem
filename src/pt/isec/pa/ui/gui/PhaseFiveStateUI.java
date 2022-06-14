package pt.isec.pa.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.Proposals;
import pt.isec.pa.model.fsm.AppState;

import java.util.ArrayList;

public class PhaseFiveStateUI  extends BorderPane {
    Facade facade;
    Tab tabStudents, tabProposals,tabMentors, tabGraphics;
    VBox vbConsultBtn,vbConsultBtnN,vbGraphics;
    Label lbConsultStudents;
    HBox hbBtnConsultBTN,hbBtnConsultS;
    ListView lvConsultBtn;
    Button btnPropAttributesMentor, btnPropAttributesNMentor,btnNOrientMentor;
    ChoiceBox cbtitleCodeMentor;
    VBox vbConsultFilters,vbConsultS;
    Label lbConsultC,lbConsultS;
    HBox hbBtnConsultC;
    Button chPropAvailable,chPropNotAvailable,chStudentProp,chStudentNPropWc;
    ListView lvConsultP,lvConsultS;
    ArrayList<Filtros> fl;
    PieChart pcBranches,pcAtributtesNatributted;

    public PhaseFiveStateUI(Facade facade) {
        fl = new ArrayList<>();
        this.facade = facade;
        createViews();
        registerHandlers();
        update();
    }


    private void createViews() {
        //Graphics
        vbGraphics = new VBox();
        vbGraphics.setSpacing(20);
        pcBranches = new PieChart();
        PieChart.Data slice1 = new PieChart.Data("DA", facade.getBranchProposalsDA());
        PieChart.Data slice2 = new PieChart.Data("RAS", facade.getBranchProposalsRAS());
        PieChart.Data slice3 = new PieChart.Data("SI"  , facade.getBranchProposalsSI());
        pcBranches.getData().addAll(slice2,slice1,slice3);

        pcAtributtesNatributted = new PieChart();
        PieChart.Data slice4 = new PieChart.Data("Atributted Proposals", facade.getNotAvailablePropNumber());
        PieChart.Data slice5 = new PieChart.Data("Non atributted Proposals", facade.getAvailablePropNumber());
        pcAtributtesNatributted.getData().addAll(slice4,slice5);
        vbGraphics.getChildren().addAll(pcBranches,pcAtributtesNatributted);

        //mentor
        lbConsultStudents = new Label("Choose which group of People you want to see");
        lbConsultStudents.setAlignment(Pos.CENTER);
        lbConsultStudents.setId("lbTitle");
        lbConsultStudents.setMinWidth(800);
        hbBtnConsultBTN = new HBox();
        hbBtnConsultBTN.setAlignment(Pos.CENTER);
        hbBtnConsultBTN.setSpacing(5);
        vbConsultBtn = new VBox();
        vbConsultBtn.setSpacing(20);
        vbConsultBtn.setAlignment(Pos.CENTER);
        lvConsultBtn = new ListView();
        lvConsultBtn.setEditable(false);
        btnPropAttributesMentor  = new Button("Students with Proposal with Mentor         ");
        btnPropAttributesNMentor = new Button("Students with Proposal with no Mentor       ");
        btnNOrientMentor = new Button("Number of Proposals a Teacher Mentors      ");
        cbtitleCodeMentor =new ChoiceBox<>();
        vbConsultBtnN = new VBox();
        vbConsultBtnN.setSpacing(7);
        vbConsultBtnN.getChildren().addAll(btnNOrientMentor,cbtitleCodeMentor);

        hbBtnConsultBTN.getChildren().addAll( btnPropAttributesMentor,btnPropAttributesNMentor,vbConsultBtnN);
        vbConsultBtn.getChildren().addAll(lbConsultStudents,lvConsultBtn, hbBtnConsultBTN);



        //Prop
        vbConsultFilters = new VBox();
        vbConsultFilters.setSpacing(20);
        vbConsultFilters.setAlignment(Pos.CENTER);
        lbConsultC = new Label("Select which group of Proposals you want to see");
        lbConsultC.setAlignment(Pos.CENTER);
        lbConsultC.setId("lbTitle");
        lbConsultC.setMinWidth(800);
        chPropAvailable = new Button("Available Proposals      ");
        chPropNotAvailable = new Button("Non Available Proposals      ");
        hbBtnConsultC = new HBox();
        hbBtnConsultC.setAlignment(Pos.CENTER);
        hbBtnConsultC.setSpacing(5);
        hbBtnConsultC.getChildren().addAll(chPropAvailable, chPropNotAvailable);
        lvConsultP = new ListView();
        vbConsultFilters.getChildren().addAll(lbConsultC,lvConsultP,hbBtnConsultC);
        lvConsultP.setEditable(false);

        //Students
        vbConsultS = new VBox();
        vbConsultS.setSpacing(20);
        vbConsultS.setAlignment(Pos.CENTER);
        lbConsultS = new Label("Select which group of Students you want to see");
        lbConsultS.setAlignment(Pos.CENTER);
        lbConsultS.setId("lbTitle");
        lbConsultS.setMinWidth(800);
        hbBtnConsultS = new HBox();
        hbBtnConsultS.setAlignment(Pos.CENTER);
        chStudentProp = new Button("Students with Proposal      ");
        chStudentNPropWc = new Button("Students without a Proposal      ");
        hbBtnConsultS.setSpacing(5);
        hbBtnConsultS.getChildren().addAll(chStudentProp, chStudentNPropWc);
        lvConsultS = new ListView();
        vbConsultS.getChildren().addAll(lbConsultS,lvConsultS,hbBtnConsultS);
        lvConsultS.setEditable(false);

    private void registerHandlers() {
        facade.addPropertyChangeListener(evt -> { update(); });

        btnPropAttributesMentor.setOnAction(actionEvent -> {
            lvConsultBtn.getItems().clear();
            if(facade.studentsMentor().isEmpty())
                return;

            for(Student s  :  facade.studentsMentor())
                lvConsultBtn.getItems().add(s.toString());
        });

        btnPropAttributesNMentor.setOnAction(actionEvent -> {
            lvConsultBtn.getItems().clear();
            if(facade.studentsMentor().isEmpty())
                return;

            for(Student s : facade.studentsNoMentor())
                lvConsultBtn.getItems().add(s.toString());
        });

        btnNOrientMentor.setOnAction(actionEvent -> {
            lvConsultBtn.getItems().clear();
            StringBuilder sb = new StringBuilder();
            if(cbtitleCodeMentor.getValue() == null){
                for(String s : facade.getTeachers().keySet()){
                    sb.append("Teacher: "+ facade.getTeachers().get(s).toString()).append("\n");
                    sb.append("Min: "+ facade.mentorByTeacher(s).get(0)).append("\n");
                    sb.append("Max: "+ facade.mentorByTeacher(s).get(1)).append("\n");
                    sb.append("Average: "+ facade.mentorByTeacher(s).get(3)).append("\n");
                    lvConsultBtn.getItems().add(sb.toString());
                    sb = new StringBuilder();
                }

            }else{
                Label labelresponse = new Label();
                labelresponse.setText("" + cbtitleCodeMentor.getValue());

                String s = labelresponse.toString();

                String[] values;
                values = s.split(",");
                values[1] = values[1].substring(0, values[1].length() - 1);
                String email = values[1];

                sb.append("Teacher: "+ facade.getTeachers().get(email).toString()).append("\n");
                sb.append("Min: "+ facade.mentorByTeacher(email).get(0)).append("\n");
                sb.append("Max: "+ facade.mentorByTeacher(email).get(1)).append("\n");
                sb.append("Teacher Count: "+ facade.getTeachers().get(email).getMentorCount()).append("\n");
                sb.append("Average: "+ facade.mentorByTeacher(email).get(3)).append("\n");

                lvConsultBtn.getItems().add(sb.toString());
            }

            cbtitleCodeMentor.setValue(null);
        });

        chPropAvailable.setOnAction(actionEvent -> {
            fl.add(Filtros.AVAIABLE);
            lvConsultP.getItems().clear();
            for(Proposals p  : facade.printFiltros(fl))
                lvConsultP.getItems().add(p.toString());
            fl.clear();
        });

        chPropNotAvailable.setOnAction(actionEvent -> {
            fl.add(Filtros.NOTAVAIABLE);

            lvConsultP.getItems().clear();
            for(Proposals p  : facade.printFiltros(fl))
                lvConsultP.getItems().add(p.toString());
            fl.clear();
        });

        chStudentProp.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            for(Student s  : facade.studentsWFA())
                lvConsultS.getItems().add(s.toString());
        });

        chStudentNPropWc.setOnAction(actionEvent -> {
            lvConsultS.getItems().clear();
            for(Student s  : facade.studentsWOPropAssociated())
                lvConsultS.getItems().add(s.toString());
        });
    }

    private void update() {

        cbtitleCodeMentor.getItems().clear();
        for(Teacher t : facade.getTeachers().values()) {
            cbtitleCodeMentor.getItems().add(t.getName()+","+t.getEmail());
        }

        if (facade.getState() != AppState.PHASE_FIVE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
