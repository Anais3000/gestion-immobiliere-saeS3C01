package modele.dao.requetes.chiffres_cles;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner total des travaux effectués sur un bien d'un
 * id spécifié sur une année spécifiée
 */
public class RequeteSelectTotalMontantTravauxParBienEtAnnee extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "select sum(nvl(i.montant_facture,0)) AS somme, o.nom AS organisme \r\n"
				+ "from sae_intervention i, sae_organisme o, sae_necessiter n\r\n"
				+ "where i.id_intervention = n.id_intervention\r\n" + "and i.num_siret = o.num_siret\r\n"
				+ "and n.id_bien = ?\r\n" + "and EXTRACT(YEAR from date_intervention) = ?\r\n" + "group by o.nom";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		int annee = Integer.parseInt(id[1]);
		prSt.setInt(2, annee);
	}
}
