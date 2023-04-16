import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'demandeur',
        data: { pageTitle: 'jhipsterSampleApplicationApp.demandeur.home.title' },
        loadChildren: () => import('./demandeur/demandeur.module').then(m => m.DemandeurModule),
      },
      {
        path: 'demande-de-traduction',
        data: { pageTitle: 'jhipsterSampleApplicationApp.demandeDeTraduction.home.title' },
        loadChildren: () => import('./demande-de-traduction/demande-de-traduction.module').then(m => m.DemandeDeTraductionModule),
      },
      {
        path: 'document-a-traduire',
        data: { pageTitle: 'jhipsterSampleApplicationApp.documentATraduire.home.title' },
        loadChildren: () => import('./document-a-traduire/document-a-traduire.module').then(m => m.DocumentATraduireModule),
      },
      {
        path: 'devis',
        data: { pageTitle: 'jhipsterSampleApplicationApp.devis.home.title' },
        loadChildren: () => import('./devis/devis.module').then(m => m.DevisModule),
      },
      {
        path: 'detail-devis',
        data: { pageTitle: 'jhipsterSampleApplicationApp.detailDevis.home.title' },
        loadChildren: () => import('./detail-devis/detail-devis.module').then(m => m.DetailDevisModule),
      },
      {
        path: 'nature-document-a-traduire',
        data: { pageTitle: 'jhipsterSampleApplicationApp.natureDocumentATraduire.home.title' },
        loadChildren: () =>
          import('./nature-document-a-traduire/nature-document-a-traduire.module').then(m => m.NatureDocumentATraduireModule),
      },
      {
        path: 'langue',
        data: { pageTitle: 'jhipsterSampleApplicationApp.langue.home.title' },
        loadChildren: () => import('./langue/langue.module').then(m => m.LangueModule),
      },
      {
        path: 'prestataire',
        data: { pageTitle: 'jhipsterSampleApplicationApp.prestataire.home.title' },
        loadChildren: () => import('./prestataire/prestataire.module').then(m => m.PrestataireModule),
      },
      {
        path: 'notation',
        data: { pageTitle: 'jhipsterSampleApplicationApp.notation.home.title' },
        loadChildren: () => import('./notation/notation.module').then(m => m.NotationModule),
      },
      {
        path: 'piece-jointe',
        data: { pageTitle: 'jhipsterSampleApplicationApp.pieceJointe.home.title' },
        loadChildren: () => import('./piece-jointe/piece-jointe.module').then(m => m.PieceJointeModule),
      },
      {
        path: 'terjem-theque',
        data: { pageTitle: 'jhipsterSampleApplicationApp.terjemTheque.home.title' },
        loadChildren: () => import('./terjem-theque/terjem-theque.module').then(m => m.TerjemThequeModule),
      },
      {
        path: 'banque',
        data: { pageTitle: 'jhipsterSampleApplicationApp.banque.home.title' },
        loadChildren: () => import('./banque/banque.module').then(m => m.BanqueModule),
      },
      {
        path: 'agence-banque',
        data: { pageTitle: 'jhipsterSampleApplicationApp.agenceBanque.home.title' },
        loadChildren: () => import('./agence-banque/agence-banque.module').then(m => m.AgenceBanqueModule),
      },
      {
        path: 'ville',
        data: { pageTitle: 'jhipsterSampleApplicationApp.ville.home.title' },
        loadChildren: () => import('./ville/ville.module').then(m => m.VilleModule),
      },
      {
        path: 'commentaire',
        data: { pageTitle: 'jhipsterSampleApplicationApp.commentaire.home.title' },
        loadChildren: () => import('./commentaire/commentaire.module').then(m => m.CommentaireModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
