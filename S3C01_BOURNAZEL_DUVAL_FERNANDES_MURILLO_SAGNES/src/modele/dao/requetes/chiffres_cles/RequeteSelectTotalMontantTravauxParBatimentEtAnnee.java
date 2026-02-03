package modele.dao.requetes.chiffres_cles;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner total des travaux effectués sur un batiment
 * d'un id spécifié sur une année spécifiée
 */
public class RequeteSelectTotalMontantTravauxParBatimentEtAnnee extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "select sum(nvl(i.montant_facture,0)) as somme, o.nom as organisme \r\n"
				+ "from sae_intervention i, sae_organisme o\r\n" + "where i.num_siret = o.num_siret\r\n"
				+ "and i.id_Batiment = ?\r\n" + "and Extract(YEAR from date_intervention) = ?\r\n"
				+ "and i.id_intervention not in (select id_intervention from sae_necessiter)\r\n" + "group by o.nom";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		int annee = Integer.parseInt(id[1]);
		prSt.setInt(2, annee);
	}
}
