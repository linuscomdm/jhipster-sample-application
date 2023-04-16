import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrestataireFormService } from './prestataire-form.service';
import { PrestataireService } from '../service/prestataire.service';
import { IPrestataire } from '../prestataire.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';
import { IVille } from 'app/entities/ville/ville.model';
import { VilleService } from 'app/entities/ville/service/ville.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';
import { IAgenceBanque } from 'app/entities/agence-banque/agence-banque.model';
import { AgenceBanqueService } from 'app/entities/agence-banque/service/agence-banque.service';

import { PrestataireUpdateComponent } from './prestataire-update.component';

describe('Prestataire Management Update Component', () => {
  let comp: PrestataireUpdateComponent;
  let fixture: ComponentFixture<PrestataireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prestataireFormService: PrestataireFormService;
  let prestataireService: PrestataireService;
  let userService: UserService;
  let banqueService: BanqueService;
  let villeService: VilleService;
  let demandeDeTraductionService: DemandeDeTraductionService;
  let agenceBanqueService: AgenceBanqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrestataireUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PrestataireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrestataireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prestataireFormService = TestBed.inject(PrestataireFormService);
    prestataireService = TestBed.inject(PrestataireService);
    userService = TestBed.inject(UserService);
    banqueService = TestBed.inject(BanqueService);
    villeService = TestBed.inject(VilleService);
    demandeDeTraductionService = TestBed.inject(DemandeDeTraductionService);
    agenceBanqueService = TestBed.inject(AgenceBanqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const prestataire: IPrestataire = { id: 456 };
      const user: IUser = { id: 68951 };
      prestataire.user = user;

      const userCollection: IUser[] = [{ id: 26989 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Banque query and add missing value', () => {
      const prestataire: IPrestataire = { id: 456 };
      const banque: IBanque = { id: 36033 };
      prestataire.banque = banque;

      const banqueCollection: IBanque[] = [{ id: 62290 }];
      jest.spyOn(banqueService, 'query').mockReturnValue(of(new HttpResponse({ body: banqueCollection })));
      const additionalBanques = [banque];
      const expectedCollection: IBanque[] = [...additionalBanques, ...banqueCollection];
      jest.spyOn(banqueService, 'addBanqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      expect(banqueService.query).toHaveBeenCalled();
      expect(banqueService.addBanqueToCollectionIfMissing).toHaveBeenCalledWith(
        banqueCollection,
        ...additionalBanques.map(expect.objectContaining)
      );
      expect(comp.banquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ville query and add missing value', () => {
      const prestataire: IPrestataire = { id: 456 };
      const ville: IVille = { id: 93498 };
      prestataire.ville = ville;

      const villeCollection: IVille[] = [{ id: 49533 }];
      jest.spyOn(villeService, 'query').mockReturnValue(of(new HttpResponse({ body: villeCollection })));
      const additionalVilles = [ville];
      const expectedCollection: IVille[] = [...additionalVilles, ...villeCollection];
      jest.spyOn(villeService, 'addVilleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      expect(villeService.query).toHaveBeenCalled();
      expect(villeService.addVilleToCollectionIfMissing).toHaveBeenCalledWith(
        villeCollection,
        ...additionalVilles.map(expect.objectContaining)
      );
      expect(comp.villesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeDeTraduction query and add missing value', () => {
      const prestataire: IPrestataire = { id: 456 };
      const prestaDdeTraductions: IDemandeDeTraduction = { id: 17994 };
      prestataire.prestaDdeTraductions = prestaDdeTraductions;

      const demandeDeTraductionCollection: IDemandeDeTraduction[] = [{ id: 24255 }];
      jest.spyOn(demandeDeTraductionService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDeTraductionCollection })));
      const additionalDemandeDeTraductions = [prestaDdeTraductions];
      const expectedCollection: IDemandeDeTraduction[] = [...additionalDemandeDeTraductions, ...demandeDeTraductionCollection];
      jest.spyOn(demandeDeTraductionService, 'addDemandeDeTraductionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      expect(demandeDeTraductionService.query).toHaveBeenCalled();
      expect(demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDeTraductionCollection,
        ...additionalDemandeDeTraductions.map(expect.objectContaining)
      );
      expect(comp.demandeDeTraductionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AgenceBanque query and add missing value', () => {
      const prestataire: IPrestataire = { id: 456 };
      const agenceBanque: IAgenceBanque = { id: 92977 };
      prestataire.agenceBanque = agenceBanque;

      const agenceBanqueCollection: IAgenceBanque[] = [{ id: 96699 }];
      jest.spyOn(agenceBanqueService, 'query').mockReturnValue(of(new HttpResponse({ body: agenceBanqueCollection })));
      const additionalAgenceBanques = [agenceBanque];
      const expectedCollection: IAgenceBanque[] = [...additionalAgenceBanques, ...agenceBanqueCollection];
      jest.spyOn(agenceBanqueService, 'addAgenceBanqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      expect(agenceBanqueService.query).toHaveBeenCalled();
      expect(agenceBanqueService.addAgenceBanqueToCollectionIfMissing).toHaveBeenCalledWith(
        agenceBanqueCollection,
        ...additionalAgenceBanques.map(expect.objectContaining)
      );
      expect(comp.agenceBanquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const prestataire: IPrestataire = { id: 456 };
      const user: IUser = { id: 71438 };
      prestataire.user = user;
      const banque: IBanque = { id: 16042 };
      prestataire.banque = banque;
      const ville: IVille = { id: 20940 };
      prestataire.ville = ville;
      const prestaDdeTraductions: IDemandeDeTraduction = { id: 59574 };
      prestataire.prestaDdeTraductions = prestaDdeTraductions;
      const agenceBanque: IAgenceBanque = { id: 2225 };
      prestataire.agenceBanque = agenceBanque;

      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.banquesSharedCollection).toContain(banque);
      expect(comp.villesSharedCollection).toContain(ville);
      expect(comp.demandeDeTraductionsSharedCollection).toContain(prestaDdeTraductions);
      expect(comp.agenceBanquesSharedCollection).toContain(agenceBanque);
      expect(comp.prestataire).toEqual(prestataire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrestataire>>();
      const prestataire = { id: 123 };
      jest.spyOn(prestataireFormService, 'getPrestataire').mockReturnValue(prestataire);
      jest.spyOn(prestataireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prestataire }));
      saveSubject.complete();

      // THEN
      expect(prestataireFormService.getPrestataire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prestataireService.update).toHaveBeenCalledWith(expect.objectContaining(prestataire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrestataire>>();
      const prestataire = { id: 123 };
      jest.spyOn(prestataireFormService, 'getPrestataire').mockReturnValue({ id: null });
      jest.spyOn(prestataireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prestataire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prestataire }));
      saveSubject.complete();

      // THEN
      expect(prestataireFormService.getPrestataire).toHaveBeenCalled();
      expect(prestataireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrestataire>>();
      const prestataire = { id: 123 };
      jest.spyOn(prestataireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prestataire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prestataireService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareBanque', () => {
      it('Should forward to banqueService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(banqueService, 'compareBanque');
        comp.compareBanque(entity, entity2);
        expect(banqueService.compareBanque).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareVille', () => {
      it('Should forward to villeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(villeService, 'compareVille');
        comp.compareVille(entity, entity2);
        expect(villeService.compareVille).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDemandeDeTraduction', () => {
      it('Should forward to demandeDeTraductionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(demandeDeTraductionService, 'compareDemandeDeTraduction');
        comp.compareDemandeDeTraduction(entity, entity2);
        expect(demandeDeTraductionService.compareDemandeDeTraduction).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAgenceBanque', () => {
      it('Should forward to agenceBanqueService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(agenceBanqueService, 'compareAgenceBanque');
        comp.compareAgenceBanque(entity, entity2);
        expect(agenceBanqueService.compareAgenceBanque).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
