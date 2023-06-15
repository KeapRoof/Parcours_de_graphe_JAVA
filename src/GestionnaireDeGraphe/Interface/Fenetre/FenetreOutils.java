package GestionnaireDeGraphe.Interface.Fenetre;

import GestionnaireDeGraphe.Interface.ElementDeStructure.AreteGraphe;
import GestionnaireDeGraphe.Interface.ElementDeStructure.SommetGraphe;
import GestionnaireDeGraphe.TraitementGraphe.LCGraphe;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class FenetreOutils extends JFrame {

    private LCGraphe graphe;
    private List<LCGraphe.MaillonGraphe> listeSommets;
    private HashMap<String, LCGraphe.MaillonGrapheSec> listeArete;
    private List<AreteGraphe> listearretegraphique;
    private List<SommetGraphe> listesommetgraphique;
    private FenetreChargementGraphe fenetreChargementGraphe;

    //    Tout ce qui est relatif au menU
    private JMenuBar menu;
    private JMenu traitement;
    private JMenuItem reinit;
    private JMenuItem distance1;
    private JMenuItem parcours;
    private JMenuItem distance2;

//    Tout ce qui est relatif au contenu de la fenetre
    private JLabel titre;
    private JPanel titrePanel;
    private JTextArea textArea;
    private JPanel content;

    public FenetreOutils(LCGraphe graphe, List<LCGraphe.MaillonGraphe> listeSommets, HashMap<String, LCGraphe.MaillonGrapheSec> listeArete, List<AreteGraphe> listearretegraphique, List<SommetGraphe> listesommetgraphique, FenetreChargementGraphe fenetreChargementGraphe) {
        this.graphe = graphe;
        this.listearretegraphique = listearretegraphique;
        this.listesommetgraphique = listesommetgraphique;
        this.fenetreChargementGraphe = fenetreChargementGraphe;
        this.listeSommets = listeSommets;
        this.listeArete = listeArete;
        initComponents();
        initActionListener();
        this.pack();
    }

    private void initComponents() {
        this.setTitle("Outils");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(400,500));
        this.setVisible(true);

        this.menu = new JMenuBar();
        traitement = new JMenu("Traitement");
        reinit = new JMenuItem("Reinitialiser");
        distance1 = new JMenuItem("Distance 1");
        distance2 = new JMenuItem("Distance 2");
        parcours = new JMenuItem("Parcours");

        this.traitement.add(reinit);
        this.traitement.add(distance1);
        this.traitement.add(distance2);
        this.traitement.add(parcours);
        this.menu.add(traitement);
        this.setJMenuBar(this.menu);

        this.textArea = new JTextArea();
        this.textArea.setPreferredSize(new Dimension(360, 450));
        this.textArea.setEditable(false);
        this.textArea.setText("Effectuer un traitement\nsur le graphe");

//        this.titre = new JLabel("Outils");
//
//        this.titrePanel = new JPanel();
//        this.titrePanel.add(this.titre);
//        this.titrePanel.setBorder(BorderFactory.createLineBorder(Color.green));

        this.content = new JPanel();
        this.content.setBorder(BorderFactory.createLineBorder(Color.red));
        this.content.add(this.textArea);

//        this.add(this.titrePanel);
        this.add(this.content);
    }

    private void initWindow() {
        this.setTitle("Outils");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(250,400));
        this.setVisible(true);
    }

    public void reinitCouleurSommet(){
        for (LCGraphe.MaillonGraphe listeSommet : listeSommets) {
            for (SommetGraphe sommetGraphe : this.listesommetgraphique) {
                if (sommetGraphe.getNomSommet().equals(listeSommet.getNom()))
                    sommetGraphe.setCouleurDuPoint(listeSommet.getType().getColor());
            }
        }
    }
    public void reinitCouleurArete(){
        for (AreteGraphe areteGraphe : this.listearretegraphique) {
            areteGraphe.setCouleurActuelle(Color.black);
        }
    }

    private void initActionListener() {
        this.reinit.addActionListener(e -> {
            reinitCouleurArete();
            reinitCouleurSommet();
            fenetreChargementGraphe.repaint();
        });
        this.distance1.addActionListener(e -> {
            reinitCouleurArete();
            reinitCouleurSommet();
            String nomSommet = JOptionPane.showInputDialog(this, "Entrez le nom du sommet", "Distance 1", JOptionPane.QUESTION_MESSAGE);

            java.util.List<LCGraphe.MaillonGrapheSec> lAretes = this.graphe.getListAretesAdj(graphe.recherchenom(nomSommet));
            for (LCGraphe.MaillonGrapheSec aretesADessiner : lAretes) {
                for (AreteGraphe areteGraphe : this.listearretegraphique) {
                    if (areteGraphe.getAreteNom().equals(aretesADessiner.getNomArete())) {
                        areteGraphe.setCouleurActuelle(Color.red);
                    }
                }
                fenetreChargementGraphe.repaint();
            }

            java.util.List<LCGraphe.MaillonGraphe> lSommets = this.graphe.getListSommetAdj(graphe.recherchenom(nomSommet));
            for (LCGraphe.MaillonGraphe sommetADessiner : lSommets) {
                for (SommetGraphe sommetGraphe : this.listesommetgraphique) {
                    if (sommetGraphe.getNomSommet().equals(sommetADessiner.getNom())) {
                        sommetGraphe.setCouleurDuPoint(Color.green);
//                        TODO fix le fait que lorsqu'on déplcale le point il rechange de couleur
                    }
                }
                fenetreChargementGraphe.repaint();
            }
            this.textArea.setText("Sommet adjacent : " + lSommets.toString() + "\n" + "Arete adjacent : " + lAretes.toString());
        });
        this.distance2.addActionListener(e -> {
            reinitCouleurArete();
            reinitCouleurSommet();
            String nomSommet = JOptionPane.showInputDialog(this, "Entrez le nom du sommet", "Distance 2", JOptionPane.QUESTION_MESSAGE);

            java.util.List<LCGraphe.MaillonGrapheSec> lAretes = this.graphe.getListArete2Distance(graphe.recherchenom(nomSommet));
            for (LCGraphe.MaillonGrapheSec aretesADessiner : lAretes) {
                for (AreteGraphe areteGraphe : this.listearretegraphique) {
                    if (areteGraphe.getAreteNom().equals(aretesADessiner.getNomArete())) {
                        areteGraphe.setCouleurActuelle(Color.red);
                    }
                }
                fenetreChargementGraphe.repaint();
            }

            java.util.List<LCGraphe.MaillonGraphe> lSommets = this.graphe.getListSommet2Dist(graphe.recherchenom(nomSommet));
            for (LCGraphe.MaillonGraphe sommetADessiner : lSommets) {
                for (SommetGraphe sommetGraphe : this.listesommetgraphique) {
                    if (sommetGraphe.getNomSommet().equals(sommetADessiner.getNom())) {
                        sommetGraphe.setCouleurDuPoint(Color.green);
//                        TODO fix le fait que lorsqu'on déplcale le point il rechange de couleur
                    }
                }
                fenetreChargementGraphe.repaint();
            }
            this.textArea.setText("Sommet adjacent : " + lSommets.toString() + "\n" + "Arete adjacent : " + lAretes.toString());
        });
        this.parcours.addActionListener(e -> {
            reinitCouleurArete();
            reinitCouleurSommet();
            JTextField ori = new JTextField(2);
            JTextField dest = new JTextField(2);
            JComboBox<String> combo = new JComboBox<>();
            String choix1 = "Rapide";
            String choix2 = "Court";
            String choix3 = "Fiable";
            combo.addItem(choix1);
            combo.addItem(choix2);
            combo.addItem(choix3);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Origine:"));
            myPanel.add(ori);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Arrive:"));
            myPanel.add(dest);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Choix:"));
            myPanel.add(combo);
            int result = JOptionPane.showConfirmDialog(null, myPanel,"Entrer vos sommet de départ et d'arrivé", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                switch (combo.getSelectedItem().toString()) {
                    case "Rapide":
                        java.util.List a1 = this.graphe.dijkstrarapide(ori.getText(),dest.getText());
                        java.util.List arc1 = (java.util.List) a1.get(1);
                        for (Object obj : arc1) {
                            //recuperer l'arc listearretegraphique
                            for (AreteGraphe arete : this.listearretegraphique) {
                                if (arete.getAreteNom().equals(obj)) {
                                    arete.setCouleurActuelle(Color.red);
                                }
                            }
                        }
                        fenetreChargementGraphe.repaint();
                        this.textArea.setText("Le chemin le plus rapide est : " + a1.get(1) + "\n" + "Les sommets empruntés sont : " + a1.get(0));
                        break;
                    case "Court":
                        java.util.List a2 = this.graphe.dijkstracourt(ori.getText(),dest.getText());
                        java.util.List arc2 = (java.util.List) a2.get(1);
                        for (Object obj : arc2) {
                            //recuperer l'arc listearretegraphique
                            for (AreteGraphe arete : this.listearretegraphique) {
                                if (arete.getAreteNom().equals(obj)) {
                                    arete.setCouleurActuelle(Color.red);
                                }
                            }
                        }
                        fenetreChargementGraphe.repaint();
                        this.textArea.setText("Le chemin le plus court est : " + a2.get(1) + "\n" + "Les sommets empruntés sont : " + a2.get(0));
                        break;
                    case "Fiable":
                        java.util.List a3 = this.graphe.dijkstrafiable(ori.getText(),dest.getText());
                        java.util.List arc3 = (java.util.List) a3.get(1);
                        for (Object obj : arc3) {
                            //recuperer l'arc listearretegraphique
                            for (AreteGraphe arete : this.listearretegraphique) {
                                if (arete.getAreteNom().equals(obj)) {
                                    arete.setCouleurActuelle(Color.red);
                                }
                            }
                        }
                        fenetreChargementGraphe.repaint();
                        this.textArea.setText("Le chemin le plus fiable est : " + a3.get(1) + "\n" + "Les sommets empruntés sont : " + a3.get(0));
                        break;
                }
                java.util.List a = this.graphe.dijkstracourt(ori.getText(),dest.getText());
                java.util.List arc = (List) a.get(1);
                for (Object obj : arc) {
                    //recuperer l'arc listearretegraphique
                    for (AreteGraphe arete : this.listearretegraphique) {
                        if (arete.getAreteNom().equals(obj)) {
                            arete.setCouleurActuelle(Color.red);
                        }
                    }
                }
                fenetreChargementGraphe.repaint();
            }
        });
    }
}