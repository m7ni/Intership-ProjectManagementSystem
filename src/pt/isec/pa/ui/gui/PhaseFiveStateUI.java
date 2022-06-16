package pt.isec.pa.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pt.isec.pa.model.Facade;
import pt.isec.pa.model.data.Filtros;
import pt.isec.pa.model.data.personel.Student;
import pt.isec.pa.model.data.personel.Teacher;
import pt.isec.pa.model.data.proposals.Proposals;
import pt.isec.pa.model.fsm.AppState;
import pt.isec.pa.model.fsm.Constants;


import java.util.ArrayList;
import java.util.HashMap;

public class PhaseFiveStateUI  extends BorderPane {
    Facade facade;
    Tab tabStudents, tabProposals,tabMentors, tabGraphics;
    VBox vbConsultBtn,vbConsultBtnN,vbGraphics, vbspGraphics;
    Label lbConsultStudents, lbTeacherEmailConsultMentors;
    HBox hbBtnConsultBTN,hbBtnConsultS, hbTeacherEmailConsultMentors;
    ListView lvConsultBtn;
    Button btnPropAttributesMentor, btnPropAttributesNMentor,btnNOrientMentor;
    ChoiceBox cbtitleCodeMentor;
    VBox vbConsultFilters,vbConsultS;
    Label lbConsultC,lbConsultS;
    HBox hbBtnConsultC;
    Button chPropAvailable,chPropNotAvailable,chStudentProp,chStudentNPropWc;
    ListView lvConsultP,lvConsultS;
    ArrayList<Filtros> fl;
    PieChart pcBranches,pcAtributtesNatributted, pcMentorCount;
    ScrollPane spGraphics;

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
        vbGraphics.setAlignment(Pos.CENTER);
        pcBranches = new PieChart();
        pcMentorCount = new PieChart();
        spGraphics = new ScrollPane();
        pcAtributtesNatributted = new PieChart();
        vbspGraphics = new VBox();
        vbspGraphics.setSpacing(20);
        vbspGraphics.setAlignment(Pos.CENTER);

        vbGraphics.getChildren().addAll(pcMentorCount,pcBranches,pcAtributtesNatributted);

        spGraphics.setContent(vbGraphics);
        spGraphics.setMaxHeight(800);
        vbspGraphics.getChildren().add(spGraphics);

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

        lbTeacherEmailConsultMentors = new Label("Mentor\t");
        lbTeacherEmailConsultMentors.setAlignment(Pos.CENTER_LEFT);

        hbTeacherEmailConsultMentors = new HBox(lbTeacherEmailConsultMentors, cbtitleCodeMentor);

        hbTeacherEmailConsultMentors.setAlignment(Pos.CENTER);
        hbTeacherEmailConsultMentors.setId(  "hBoxChoice");

        vbConsultBtnN = new VBox();
        vbConsultBtnN.setSpacing(7);
        vbConsultBtnN.getChildren().addAll(btnNOrientMentor,hbTeacherEmailConsultMentors);

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


        tabGraphics = new Tab("Graphic Visualisation", vbspGraphics);
        tabGraphics.setClosable(false);
        tabStudents = new Tab("Consult Students", vbConsultS);
        tabStudents.setClosable(false);
        tabProposals = new Tab("Consult Proposals"  , vbConsultFilters);
        tabProposals.setClosable(false);
        tabMentors = new Tab("Consult Mentors"  , vbConsultBtn);
        tabMentors.setClosable(false);


        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(tabStudents);
        tabPane.getTabs().add(tabProposals);
        tabPane.getTabs().add(tabMentors);
        tabPane.getTabs().add(tabGraphics);

        VBox VBox = new VBox(tabPane);
        this.setTop(VBox);
        VBox.setAlignment(Pos.CENTER);
    }
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

        pcMentorCount.getData().clear();
        PieChart.Data sliceMentor1;
        PieChart.Data sliceMentor2;
        PieChart.Data sliceMentor3;
        PieChart.Data sliceMentor4;
        PieChart.Data sliceMentor5;
        int i=0;
        if(facade.getState() == AppState.PHASE_FIVE) {
            for (Teacher t : facade.getMapCountMentor()) {
                switch (i) {
                    case 0 -> {
                        sliceMentor1 = new PieChart.Data(t.getName(), t.getMentorCount());
                        pcMentorCount.getData().add(sliceMentor1);
                    }
                    case 1 -> {
                        sliceMentor2 = new PieChart.Data(t.getName(), t.getMentorCount());
                        pcMentorCount.getData().add(sliceMentor2);
                    }
                    case 2 -> {
                        sliceMentor3 = new PieChart.Data(t.getName(), t.getMentorCount());
                        pcMentorCount.getData().add(sliceMentor3);
                    }
                    case 3 -> {
                        sliceMentor4 = new PieChart.Data(t.getName(), t.getMentorCount());
                        pcMentorCount.getData().add(sliceMentor4);
                    }
                    case 4 -> {
                        sliceMentor5 = new PieChart.Data(t.getName(), t.getMentorCount());
                        pcMentorCount.getData().add(sliceMentor5);
                    }
                }
                i++;
            }

            PieChart.Data slice1 = new PieChart.Data("DA", facade.getBranchProposalsDA());
            PieChart.Data slice2 = new PieChart.Data("RAS", facade.getBranchProposalsRAS());
            PieChart.Data slice3 = new PieChart.Data("SI", facade.getBranchProposalsSI());
            pcBranches.getData().addAll(slice2,slice1,slice3);


            PieChart.Data slice4 = new PieChart.Data("Atributted Proposals", facade.getNotAvailablePropNumber());
            PieChart.Data slice5 = new PieChart.Data("Non atributted Proposals", facade.getAvailablePropNumber());
            pcAtributtesNatributted.getData().addAll(slice4,slice5);
        }

        /*
        if(!facade.getMapCountMentor().isEmpty() && facade.getState() == AppState.PHASE_FIVE) {
            PieChart.Data sliceMentor1 = new PieChart.Data(facade.getMapCountMentor().get(0).getName(), facade.getMapCountMentor().get(0).getMentorCount());
            PieChart.Data sliceMentor2 = new PieChart.Data(facade.getMapCountMentor().get(1).getName(), facade.getMapCountMentor().get(1).getMentorCount());
            PieChart.Data sliceMentor3 = new PieChart.Data(facade.getMapCountMentor().get(2).getName(), facade.getMapCountMentor().get(2).getMentorCount());
            PieChart.Data sliceMentor4 = new PieChart.Data(facade.getMapCountMentor().get(3).getName(), facade.getMapCountMentor().get(3).getMentorCount());
            PieChart.Data sliceMentor5 = new PieChart.Data(facade.getMapCountMentor().get(4).getName(), facade.getMapCountMentor().get(4).getMentorCount());
            pcMentorCount.getData().addAll(sliceMentor1,sliceMentor2,sliceMentor3,sliceMentor4,sliceMentor5);
        }
*/
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
