import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemandeurFormService } from './demandeur-form.service';
import { DemandeurService } from '../service/demandeur.service';
import { IDemandeur } from '../demandeur.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IVille } from 'app/entities/ville/ville.model';
import { VilleService } from 'app/entities/ville/service/ville.service';
import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';
import { IAgenceBanque } from 'app/entities/agence-banque/agence-banque.model';
import { AgenceBanqueService } from 'app/entities/agence-banque/service/agence-banque.service';

import { DemandeurUpdateComponent } from './demandeur-update.component';

describe('Demandeur Management Update Component', () => {
  let comp: DemandeurUpdateComponent;
  let fixture: ComponentFixture<DemandeurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeurFormService: DemandeurFormService;
  let demandeurService: DemandeurService;
  let userService: UserService;
  let villeService: VilleService;
  let banqueService: BanqueService;
  let agenceBanqueService: AgenceBanqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemandeurUpdateComponent],
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
      .overrideTemplate(DemandeurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeurFormService = TestBed.inject(DemandeurFormService);
    demandeurService = TestBed.inject(DemandeurService);
    userService = TestBed.inject(UserService);
    villeService = TestBed.inject(VilleService);
    banqueService = TestBed.inject(BanqueService);
    agenceBanqueService = TestBed.inject(AgenceBanqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const user: IUser = { id: 84134 };
      demandeur.user = user;

      const userCollection: IUser[] = [{ id: 10537 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ville query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const ville: IVille = { id: 61795 };
      demandeur.ville = ville;

      const villeCollection: IVille[] = [{ id: 79821 }];
      jest.spyOn(villeService, 'query').mockReturnValue(of(new HttpResponse({ body: villeCollection })));
      const additionalVilles = [ville];
      const expectedCollection: IVille[] = [...additionalVilles, ...villeCollection];
      jest.spyOn(villeService, 'addVilleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(villeService.query).toHaveBeenCalled();
      expect(villeService.addVilleToCollectionIfMissing).toHaveBeenCalledWith(
        villeCollection,
        ...additionalVilles.map(expect.objectContaining)
      );
      expect(comp.villesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Banque query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const banque: IBanque = { id: 89886 };
      demandeur.banque = banque;

      const banqueCollection: IBanque[] = [{ id: 99298 }];
      jest.spyOn(banqueService, 'query').mockReturnValue(of(new HttpResponse({ body: banqueCollection })));
      const additionalBanques = [banque];
      const expectedCollection: IBanque[] = [...additionalBanques, ...banqueCollection];
      jest.spyOn(banqueService, 'addBanqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(banqueService.query).toHaveBeenCalled();
      expect(banqueService.addBanqueToCollectionIfMissing).toHaveBeenCalledWith(
        banqueCollection,
        ...additionalBanques.map(expect.objectContaining)
      );
      expect(comp.banquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AgenceBanque query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const agenceBanque: IAgenceBanque = { id: 7928 };
      demandeur.agenceBanque = agenceBanque;

      const agenceBanqueCollection: IAgenceBanque[] = [{ id: 67416 }];
      jest.spyOn(agenceBanqueService, 'query').mockReturnValue(of(new HttpResponse({ body: agenceBanqueCollection })));
      const additionalAgenceBanques = [agenceBanque];
      const expectedCollection: IAgenceBanque[] = [...additionalAgenceBanques, ...agenceBanqueCollection];
      jest.spyOn(agenceBanqueService, 'addAgenceBanqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(agenceBanqueService.query).toHaveBeenCalled();
      expect(agenceBanqueService.addAgenceBanqueToCollectionIfMissing).toHaveBeenCalledWith(
        agenceBanqueCollection,
        ...additionalAgenceBanques.map(expect.objectContaining)
      );
      expect(comp.agenceBanquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const demandeur: IDemandeur = { id: 456 };
      const user: IUser = { id: 90884 };
      demandeur.user = user;
      const ville: IVille = { id: 4832 };
      demandeur.ville = ville;
      const banque: IBanque = { id: 25322 };
      demandeur.banque = banque;
      const agenceBanque: IAgenceBanque = { id: 3002 };
      demandeur.agenceBanque = agenceBanque;

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.villesSharedCollection).toContain(ville);
      expect(comp.banquesSharedCollection).toContain(banque);
      expect(comp.agenceBanquesSharedCollection).toContain(agenceBanque);
      expect(comp.demandeur).toEqual(demandeur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurFormService, 'getDemandeur').mockReturnValue(demandeur);
      jest.spyOn(demandeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeur }));
      saveSubject.complete();

      // THEN
      expect(demandeurFormService.getDemandeur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeurService.update).toHaveBeenCalledWith(expect.objectContaining(demandeur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurFormService, 'getDemandeur').mockReturnValue({ id: null });
      jest.spyOn(demandeurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeur }));
      saveSubject.complete();

      // THEN
      expect(demandeurFormService.getDemandeur).toHaveBeenCalled();
      expect(demandeurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeurService.update).toHaveBeenCalled();
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

    describe('compareVille', () => {
      it('Should forward to villeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(villeService, 'compareVille');
        comp.compareVille(entity, entity2);
        expect(villeService.compareVille).toHaveBeenCalledWith(entity, entity2);
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
