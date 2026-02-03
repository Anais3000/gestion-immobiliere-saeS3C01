package modele.dao.requetes.paiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des paiements concernant un
 * locataire d'ID donné
 */
public class RequeteSelectPaiementByIdLoc extends Requete<Paiement> {
	@Override
	public String requete() {
		return "SELECT P.* " + "FROM SAE_Paiement P, SAE_Bien_louable B, SAE_Louer L " + "WHERE P.Id_Bien = B.Id_Bien "
				+ "AND L.Id_Bien = B.Id_Bien " + "AND L.Id_Locataire = ? " + "AND P.sens_paiement = 'recus'"
				+ " ORDER BY DATE_PAIEMENT DESC, MONTANT"; // que les paiements reçus pour avoir que ceux du locataire
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}
}
