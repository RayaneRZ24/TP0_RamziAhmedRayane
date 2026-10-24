package ma.emsi.ramziahmedrayane.tp0_ramziahmedrayane.service;
import jakarta.enterprise.context.Dependent;

import java.io.Serializable;
import java.util.Locale;

/**
 * Classe de service pour les modificateurs de question.
 * Un modificateur de question prend en entrée une question et retourne
 * la question modifiée.
 */
@Dependent // Portée CDI pour rendre la classe injectable par CDI dans les autres classes.
// Portée Dependent : l'instance sera supprimée quand la classe qui l'a injectée sera supprimée.
public class GenerateurReponse implements Serializable { // Serializable car CDI peut mettre l'instance en mémoire secondaire.
    private boolean roleSystemeAffiche = false;

    /**
     * Génère une réponse à une question.
     * @param question La question à modifier.
     * @param roleSysteme Le rôle système à utiliser pour la modification de la question.
     * @return La question modifiée : le rôle système en majuscule au début de la question, s'il n'est pas null,
     * suivi d'un saut de ligne,
     * puis la question en minuscule, le tout entouré de "||".
     */
    public String genererReponse(String question, String roleSysteme) {
        String lettresGauche = "azertqsdfgwxcvb";
        String lettresDroite = "yuiophjklmn";

        int compteurGauche = 0;
        int compteurDroite = 0;
        int alternances = 0;
        char mainPrecedente = ' ';

        String texte = question.toLowerCase(Locale.FRENCH);

        for (int i = 0; i < texte.length(); i++) {
            char lettre = texte.charAt(i);
            char mainActuelle;

            if (lettresGauche.indexOf(lettre) >= 0) {
                compteurGauche++;
                mainActuelle = 'G';
            } else if (lettresDroite.indexOf(lettre) >= 0) {
                compteurDroite++;
                mainActuelle = 'D';
            } else {
                // Ignore les caractères qui ne sont pas des lettres de a à z.
                continue;
            }

            if (mainPrecedente != ' ' && mainActuelle != mainPrecedente) {
                alternances++;
            }

            mainPrecedente = mainActuelle;
        }

        int total = compteurGauche + compteurDroite;
        String verdict;

        if (total == 0) {
            verdict = "Aucune lettre de a à z à analyser.";
        } else if (compteurGauche == compteurDroite) {
            verdict = "Phrase équilibrée : les deux mains font jeu égal !";
        } else if (compteurGauche > compteurDroite) {
            verdict = "Main gauche sportive : elle mène la danse !";
        } else {
            verdict = "Main droite surchargée : elle prend les commandes !";
        }

        StringBuilder resultat = new StringBuilder("||");

        // Conserve le comportement initial : le rôle apparaît une seule fois.
        if (!this.roleSystemeAffiche && roleSysteme != null) {
            resultat.append(roleSysteme.toUpperCase(Locale.FRENCH))
                    .append("\n");
            this.roleSystemeAffiche = true;
        }

        resultat.append("EMPREINTE DE FRAPPE AZERTY\n")
                .append("Lettres analysées : ").append(total).append("\n")
                .append("Main gauche : ").append(compteurGauche).append("\n")
                .append("Main droite : ").append(compteurDroite).append("\n")
                .append("Alternances entre les mains : ").append(alternances).append("\n")
                .append("Verdict : ").append(verdict).append("\n")
                .append("Règle : seules les lettres de a à z sont comptées.")
                .append("||");

        return resultat.toString();
    }
}