package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Demandeur.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Demandeur.class.getName() + ".demandes");
            createCache(cm, com.mycompany.myapp.domain.Demandeur.class.getName() + ".commentaires");
            createCache(cm, com.mycompany.myapp.domain.Demandeur.class.getName() + ".terjemTheques");
            createCache(cm, com.mycompany.myapp.domain.Demandeur.class.getName() + ".notations");
            createCache(cm, com.mycompany.myapp.domain.DemandeDeTraduction.class.getName());
            createCache(cm, com.mycompany.myapp.domain.DemandeDeTraduction.class.getName() + ".traductions");
            createCache(cm, com.mycompany.myapp.domain.DemandeDeTraduction.class.getName() + ".traductionsPjs");
            createCache(cm, com.mycompany.myapp.domain.DemandeDeTraduction.class.getName() + ".traductionsPrestas");
            createCache(cm, com.mycompany.myapp.domain.DemandeDeTraduction.class.getName() + ".devis");
            createCache(cm, com.mycompany.myapp.domain.DemandeDeTraduction.class.getName() + ".commentaires");
            createCache(cm, com.mycompany.myapp.domain.DocumentATraduire.class.getName());
            createCache(cm, com.mycompany.myapp.domain.DocumentATraduire.class.getName() + ".documens");
            createCache(cm, com.mycompany.myapp.domain.Devis.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Devis.class.getName() + ".detailDevis");
            createCache(cm, com.mycompany.myapp.domain.DetailDevis.class.getName());
            createCache(cm, com.mycompany.myapp.domain.NatureDocumentATraduire.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Langue.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Prestataire.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Prestataire.class.getName() + ".pieceJointes");
            createCache(cm, com.mycompany.myapp.domain.Prestataire.class.getName() + ".devis");
            createCache(cm, com.mycompany.myapp.domain.Prestataire.class.getName() + ".commentaires");
            createCache(cm, com.mycompany.myapp.domain.Prestataire.class.getName() + ".terjemTheques");
            createCache(cm, com.mycompany.myapp.domain.Prestataire.class.getName() + ".notations");
            createCache(cm, com.mycompany.myapp.domain.Notation.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PieceJointe.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TerjemTheque.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Banque.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Banque.class.getName() + ".agenceBanques");
            createCache(cm, com.mycompany.myapp.domain.AgenceBanque.class.getName());
            createCache(cm, com.mycompany.myapp.domain.AgenceBanque.class.getName() + ".demandeurs");
            createCache(cm, com.mycompany.myapp.domain.AgenceBanque.class.getName() + ".prestataires");
            createCache(cm, com.mycompany.myapp.domain.Ville.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Commentaire.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
