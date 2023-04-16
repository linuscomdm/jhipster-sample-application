import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IBanque } from 'app/entities/banque/banque.model';
import { IVille } from 'app/entities/ville/ville.model';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { IAgenceBanque } from 'app/entities/agence-banque/agence-banque.model';
import { Civilite } from 'app/entities/enumerations/civilite.model';
import { TypeIdentiteProfessionnelle } from 'app/entities/enumerations/type-identite-professionnelle.model';
import { EtatPrestataire } from 'app/entities/enumerations/etat-prestataire.model';

export interface IPrestataire {
  id: number;
  civilite?: Civilite | null;
  nom?: string | null;
  prenom?: string | null;
  nomCommercial?: string | null;
  telephoneTravail?: string | null;
  telephoneMobile?: string | null;
  email?: string | null;
  adresse?: string | null;
  codePostal?: string | null;
  photoDeProfil?: string | null;
  photoDeProfilContentType?: string | null;
  numeroPieceIdentite?: string | null;
  typeIdentiteProfessionnelle?: TypeIdentiteProfessionnelle | null;
  rattachIdentitePro?: string | null;
  rattachIdentiteProContentType?: string | null;
  coordonneesBancaires?: string | null;
  coordonneesBancairesContentType?: string | null;
  titulaireDuCompte?: string | null;
  ribOuRip?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  etat?: EtatPrestataire | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  banque?: Pick<IBanque, 'id' | 'libelle'> | null;
  ville?: Pick<IVille, 'id' | 'nom'> | null;
  prestaDdeTraductions?: Pick<IDemandeDeTraduction, 'id' | 'delaiDeTraitemenSouhaite'> | null;
  agenceBanque?: Pick<IAgenceBanque, 'id'> | null;
}

export type NewPrestataire = Omit<IPrestataire, 'id'> & { id: null };
