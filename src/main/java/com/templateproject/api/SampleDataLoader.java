package com.templateproject.api;

import com.github.javafaker.Faker;
import com.templateproject.api.entity.*;
import com.templateproject.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final ActorRepository actorRepository;
    private final CategoryRepository categoryRepository;
    private final EpisodeRepository episodeRepository;
    private final SerieRepository serieRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;
    private final Faker faker;
    Random random = new Random();

    public SampleDataLoader(ActorRepository actorRepository, CategoryRepository categoryRepository, SerieRepository serieRepository, EpisodeRepository episodeRepository, RoleRepository roleRepository, UserRepository userRepository, LibraryRepository libraryRepository) {
        this.serieRepository = serieRepository;
        this.actorRepository = actorRepository;
        this.categoryRepository = categoryRepository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.libraryRepository = libraryRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {

        String[] episodeThumbnails = {
                "https://static1.showtimes.com/video/320x240/game-of-thrones-series-finale---preview-137278.jpg",
                "https://diveng.rosselcdn.net/sites/default/files/dpistyles/ena_16_9_extra_big/node_1769/5196/public/thumbnails/image/38384_hbo.jpg?itok=JVjzyj6H1679468046",
                "https://cherry.img.pmdstatic.net/fit/https.3A.2F.2Fimg.2Eohmymag.2Ecom.2Farticle.2Fserie.2Fune-blague-de-tyrion-revient-depuis-la-saison-1_46f7b6a506d9e4de5bf96dfe17e68d9e172b2220.2Ejpg/480x270/quality/80/thumbnail.jpg",
                "https://cherry.img.pmdstatic.net/fit/https.3A.2F.2Fimg.2Eohmymag.2Ecom.2Farticle.2Fgame-of-thrones-saison-7.2Fgame-of-thrones_0a358eac36250ed2c656c03adbd546c5bb22bb7a.2Ejpg/1200x675/quality/80/thumbnail.jpg",
                "https://img.buzzfeed.com/buzzfeed-static/static/2017-08/6/16/asset/buzzfeed-prod-fastlane-02/sub-buzz-19751-1502049723-1.png?downsize=700%3A%2A&output-quality=auto&output-format=auto",
                "https://www.lagardedenuit.com/wall/wp-content/uploads/2017/08/20170816-hbo-gots7e06-jonsnow-compagnons2-2100x1050.jpeg",
                "https://cherry.img.pmdstatic.net/fit/https.3A.2F.2Fimg.2Egentside.2Ecom.2Farticle.2Fmedia.2Fgame-of-thrones-saison-5-episode-4_3aeb09d0b9a87bfb83c71ebc69f1c050e8bb1b4e.2Ejpg/480x270/quality/80/thumbnail.jpg",
                "https://cherry.img.pmdstatic.net/fit/https.3A.2F.2Fimg.2Egaming.2Egentside.2Ecom.2Fs3.2Ffrgsg.2F480.2Fserie.2Fdefault_2023-03-22_0aec7dfb-64ec-4bea-90cc-b13ddc66154c.2Ejpeg/480x270/quality/80/thumbnail.jpg",
                "https://metro.co.uk/wp-content/uploads/2019/04/lyanna-mormont-main-d0b2.jpg?quality=90&strip=all&zoom=1&resize=480%2C270",
                "https://static.lpnt.fr/images/2019/05/21/18860791lpw-18861334-article-jpg_6225257_1250x625.jpg"
        };

        String[] serieAvatarXl = {
                "https://cache.cosmopolitan.fr/data/photo/w1000_ci/4x/ned-stark.jpg",
                "https://www.lexpress.fr/resizer/i8FjgArCdRRq1Rjh72ZgGKqyyBE=/970x548/cloudfront-eu-central-1.images.arcpublishing.com/lexpress/WJDAZYAYF5FUHH72BDIYUA7HQY.jpg",
                "https://uploads.lebonbon.fr/source/2023/july/2046940/gg_2_1200.jpg",
                "https://www.talenteo.fr/app/uploads/2013/05/badass-quotes-by-dr-house-980x457-1486740267_1400x653-1600x692.jpg",
                "https://img.20mn.fr/_Sv2ljITTeePM8CGwOBgnyk/1200x768_ou-regarder-le-dernier-episode-de-la-serie-house-of-the-dragon",
                "https://occ-0-1432-38.1.nflxso.net/dnm/api/v6/6AYY37jfdO6hpXcMjf9Yu5cnmO0/AAAABS6v2gvwesuRN6c28ZykPq_fpmnQCJwELBU-kAmEcuC9HhWX-DfuDbtA-bfo-IrfgNtxl0qwJJlhI6DENsGFXknKkjhxPGTV-qhp.jpg?r=608",
                "https://img.nrj.fr/VCdMWgPk2YcXDDrw2yx4awUBRA4=/0x415/smart/medias%2F2022%2F07%2F62d6680cba380_62d668164ba7c.jpg",
                "https://imgsrc.cineserie.com/2020/10/secrets-de-serie-quatre-secrets-sur-lost-6.jpg?ver=1",
        };

        String[] serieAvatarXS = {
                "https://fr.web.img5.acsta.net/pictures/23/01/03/14/13/0717778.jpg",
                "https://fr.web.img6.acsta.net/pictures/22/08/29/18/20/3648785.jpg",
                "https://fr.web.img4.acsta.net/pictures/18/10/31/16/40/2925980.jpg",
                "https://fr.web.img5.acsta.net/pictures/22/04/01/11/50/1018209.jpg",
                "https://fr.web.img4.acsta.net/pictures/23/05/17/14/30/0480031.jpg",
                "https://fr.web.img4.acsta.net/pictures/22/05/18/14/31/5186184.jpg",
                "https://m.media-amazon.com/images/I/91LcizItUqL._AC_UF894,1000_QL80_.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRK2NB7jwcqGq7h6C7MfEBI0EnECaunqkxQ_DRa--F0t9lwJwVsyGcH-lDd2s6JRBRdAw0&usqp=CAU"
        };

        String[] serieTrailer = {
                "https://www.youtube.com/embed/KPLWWIOCOOQ",
                "https://www.youtube.com/embed/NaRPcVB_rD4",
                "https://www.youtube.com/embed/eCg1RN-dyQk",
                "https://www.youtube.com/embed/MczMB8nU1sY",
                "https://www.youtube.com/embed/DotnJ7tTA34",
                "https://www.youtube.com/embed/bjFVq3g59CQ",
                "https://www.youtube.com/embed/5jY1ecibLYo",
                "https://www.youtube.com/embed/KTu8iDynwNc"
        };

        String[] actorAvatar = {
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzHPnypzBWp8A4PgtBavne5HrlHd3C5zu6oA&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwscSVhzLNF_22d-Jfv5Xz3-RbBWORa7hL4w&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSImoQTu7ozOIulAaAwR8QaPu5HB6jevvS8mw&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOmpIzYbZja32K48IyMh_k4WMP9J6EE3ZCDw&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQecNk1QUSOZmj8raJs9X0sRiG1XDyLnZooMw&usqp=CAU"

        };

        List<Actor> actor = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> new Actor(
                        faker.name().firstName(), faker.name().lastName(), actorAvatar[random.nextInt(0,5)]
                )).collect(Collectors.toList());
        actorRepository.saveAll(actor);

        List<Category> category = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Category(
                        faker.book().genre()
                )).collect(Collectors.toList());
        categoryRepository.saveAll(category);

        List<Serie> series = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> {
                            int seriePictureNumber = random.nextInt(0, 8);
                            Serie serie = new Serie(
                                    faker.book().title(),
                                    faker.book().publisher(),
                                    serieAvatarXl[seriePictureNumber],
                                    serieAvatarXS[seriePictureNumber],
                                    serieTrailer[seriePictureNumber],
                                    faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                    faker.howIMetYourMother().catchPhrase(),
                                    faker.bool().bool()
                            );
                            for (int nbActor = 0; nbActor < 6; nbActor++) {
                                int randomNumber = random.nextInt(50);
                                serie.getActors().add(actor.get(randomNumber));
                            }
                            int randomNbCategory = random.nextInt(4);
                            for (int nbCategory = 0; nbCategory < randomNbCategory; nbCategory++) {
                                int randomCategory = random.nextInt(10);
                                serie.getCategories().add(category.get(randomCategory));
                            }
                            return serie;
                        }
                ).collect(Collectors.toList());
        serieRepository.saveAll(series);

        for (int nbSerie = 0; nbSerie < 25; nbSerie++) {
            LocalDate date = series.get(nbSerie).getReleaseDate();
            for (Integer nbOfSeason = 1; nbOfSeason < 3; nbOfSeason++) {
                date = date.plusYears(nbOfSeason - 1);
                for (Integer nbOfEpisodes = 1; nbOfEpisodes < 8 ; nbOfEpisodes++) {
                    date = date.plusWeeks(1);
                    Episode episode = new Episode();
                    episode.setEpisodeNumber(nbOfEpisodes);
                    episode.setDescription(faker.lorem().sentence());
                    episode.setReleaseDate(date);
                    episode.setThumbnail(episodeThumbnails[random.nextInt(0,9 )]);
                    episode.setTitle(faker.lorem().sentence());
                    episode.setSeasonNumber(nbOfSeason);
                    episode.setSerie(series.get(nbSerie));
                    episodeRepository.save(episode);
                }
            }
        }

        Role roleAdmin = new Role("admin");
        Role roleUser = new Role("user");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        List<User> users = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> {
                    if (i == 1) {
                        User userWithAdminRole = new User( "admin@gmail.com", "SuperAdmin", password.encode("password"), "", roleAdmin);
                        return userWithAdminRole;
                    } else if (i ==2 ) {
                        User userWithUserRole = new User( "user@gmail.com", "SuperUser", password.encode("password"), "", roleUser);
                        return userWithUserRole;
                    } else {
                        User user = new User(
                                faker.internet().emailAddress(),
                                faker.animal().name(),
                                faker.internet().password(),
                                faker.internet().avatar(),
                                i == 1 ? roleAdmin : roleUser
                        );
                        return user;
                    }
                }).collect(Collectors.toList());
        userRepository.saveAll(users);

        List<Library> libraries = IntStream.rangeClosed(1,100)
                .mapToObj(i -> {
                    Library library = new Library(
                        random.nextInt(10),
                        faker.friends().quote()
                    );
                    Serie serie = series.get(random.nextInt(25));
                    User user = users.get(random.nextInt(25));
                    library.setSerie(serie);
                    library.setUser(user);
                    List<Episode> episodes = episodeRepository.findBySerie(serie);
                    int nbOfEpisodes = episodes.size();
                    for (int episodesToAdd = 0; episodesToAdd < random.nextInt(10); episodesToAdd++) {
                        user.getEpisodes().add(episodes.get(random.nextInt(nbOfEpisodes)));
                    }
                    List<Episode> viewedEpisodes = user.getEpisodes();
                    userRepository.save(user);
                    if(viewedEpisodes.size() == 0) {
                        library.setStatus(LibraryStatus.valueOf("NOT_STARTED"));
                    } else if (viewedEpisodes.size() == nbOfEpisodes) {
                        library.setStatus(LibraryStatus.valueOf("FINISHED"));
                    } else {
                        library.setStatus(LibraryStatus.valueOf("IN_PROGRESS"));
                    }
                    return library;
                }).collect(Collectors.toList());
        libraryRepository.saveAll(libraries);
    }

}
