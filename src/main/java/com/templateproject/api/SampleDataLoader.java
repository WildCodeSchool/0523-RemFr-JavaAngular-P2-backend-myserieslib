package com.templateproject.api;

import com.github.javafaker.Cat;
import com.github.javafaker.Faker;
import com.templateproject.api.entity.*;
import com.templateproject.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
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
                "https://imgsrc.cineserie.com/2019/11/gossip-girl-kristen-bell-de-retour-dans-le-reboot.jpg?ver=1",
                "https://www.talenteo.fr/app/uploads/2013/05/badass-quotes-by-dr-house-980x457-1486740267_1400x653-1600x692.jpg",
                "https://img.20mn.fr/_Sv2ljITTeePM8CGwOBgnyk/1200x768_ou-regarder-le-dernier-episode-de-la-serie-house-of-the-dragon",
                "https://occ-0-1432-38.1.nflxso.net/dnm/api/v6/6AYY37jfdO6hpXcMjf9Yu5cnmO0/AAAABS6v2gvwesuRN6c28ZykPq_fpmnQCJwELBU-kAmEcuC9HhWX-DfuDbtA-bfo-IrfgNtxl0qwJJlhI6DENsGFXknKkjhxPGTV-qhp.jpg?r=608",
                "https://img.nrj.fr/VCdMWgPk2YcXDDrw2yx4awUBRA4=/0x415/smart/medias%2F2022%2F07%2F62d6680cba380_62d668164ba7c.jpg",
                "https://www.radiofrance.fr/s3/cruiser-production/2019/07/24731301-da0c-40f9-88fb-20047a3c5200/1200x680_lost-bientot-le-retour.jpg",
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

        String[] userAvatar = {
                "https://i.postimg.cc/NFzf7Zwy/ava-1.png",
                "https://i.postimg.cc/sDM2Dfp5/ava-2.png",
                "https://i.postimg.cc/Wbq2cWvn/ava-3.png",
                "https://i.postimg.cc/rpjTN718/ava-4.png",
                "https://i.postimg.cc/Vv6ktm2Q/ava-5.png",
                "https://i.postimg.cc/FHjN7Y1k/ava-6.png",
                "https://i.postimg.cc/2yVC6TBC/ava-7.png",
                "https://i.postimg.cc/2jKzJpPy/ava-8.png",
                "https://i.postimg.cc/hj3qHxZ6/ava-9.png",
                "https://i.postimg.cc/PqThZd83/ava-10.png",
                "https://i.postimg.cc/7ZvqH3Py/ava-11.png",
                "https://i.postimg.cc/KzY2qQft/ava-12.png",
                "https://i.postimg.cc/7hGyD6p6/ava-13.png",
                "https://i.postimg.cc/j2pYjTbh/ava-14.png",
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
                        User userWithAdminRole = new User( "admin@gmail.com", "SuperAdmin", password.encode("password"), "https://newprofilepic.photo-cdn.net//assets/images/article/profile.jpg?4d355bd", roleAdmin);
                        return userWithAdminRole;
                    } else if (i ==2 ) {
                        User userWithUserRole = new User( "user@gmail.com", "SuperUser", password.encode("password"), "https://newprofilepic.photo-cdn.net//assets/images/article/profile.jpg?4d355bd", roleUser);
                        return userWithUserRole;
                    } else {
                        User user = new User(
                                faker.internet().emailAddress(),
                                faker.animal().name(),
                                faker.internet().password(),
                                userAvatar[random.nextInt(0, 14)],
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

        createTheLastOfUs();
        createPeakyBlinders();
        createArcane();
    }

    private void createTheLastOfUs() {
        Serie serie = new Serie();
        serie.setIsCompleted(false);
        serie.setReleaseDate(LocalDate.now());
        serie.setPictureUrlXL("https://cdn-uploads.gameblog.fr/img/news/413901_63bbfa0946ae2.jpg");
        serie.setPictureUrlXS("https://fr.web.img2.acsta.net/pictures/23/01/12/12/36/0727474.jpg");
        serie.setDescription("Pour Joel, la survie est une préoccupation quotidienne qu'il gère à sa manière. Mais quand son chemin croise celui d'Ellie, leur voyage à travers ce qui reste des États-Unis va mettre à rude épreuve leur humanité et leur volonté de survivre.");
        serie.setName("The Last of Us");
        serie.setProducer("Craig Mazin, Neil Druckmann");
        serie.setTrailerURL("https://www.youtube.com/embed/Sh4MVJLUNNY");

        Serie savedSerie = serieRepository.save(serie);

        List<String> categoriesList = Arrays.asList(
                "Action",
                "Aventure",
                "Drame",
                "Horreur",
                "Post-apocalyptique",
                "Survie"
        );
        List<Category> savedCategories = new ArrayList<>();
        for (String categoryName : categoriesList) {
            Category existingCategory = categoryRepository.findByName(categoryName);
            if (existingCategory != null) {
                savedSerie.getCategories().add(existingCategory);
            } else {
                Category newCategory = new Category(categoryName);
                savedCategories.add(categoryRepository.save(newCategory));
                savedSerie.getCategories().add(newCategory);
            }
        }

        List<Actor> actorsList = Arrays.asList(
                new Actor("Pedro", "Pascal",
                        "https://media.senscritique.com/media/000019177556/125x166/pedro_pascal.png"),
                new Actor("Bella", "Ramsey",
                        "https://media.senscritique.com/media/000021221708/125x166/bella_ramsey.jpg"),
                new Actor("Gabriel", "Luna",
                        "https://media.senscritique.com/media/000007113123/125x166/gabriel_luna.jpg"),
                new Actor("Merle", "Dandridge",
                        "https://media.senscritique.com/media/000020539769/125x166/merle_dandridge.jpg"),
                new Actor("Nico", "Parker",
                        "https://media.senscritique.com/media/000018614921/125x166/nico_parker.jpg"),
                new Actor("Jeffrey", "Pierce",
                        "https://media.senscritique.com/media/000020539770/125x166/jeffrey_pierce.jpg")
        );
        List<Actor> savedActors = new ArrayList<>();
        for (Actor actorInfo : actorsList) {
            savedActors.add(actorRepository.save(actorInfo));
            savedSerie.getActors().add(actorInfo);
        }

        List<Episode> episodesList = Arrays.asList(
                new Episode(
                        1,
                        "Quand tu es perdu dans les ténèbres",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/452fae0f26d7a8fecbbcac06045f902b694dc4f3bd85c3e71ff63c6b1282c1e3._RI_TTW_SX480_FMwebp_.jpg",
                        "Vingt ans après qu'une épidémie fongique a ravagé la planète, les survivants Joel et Ellie sont chargés d'une mission qui pourrait tout changer.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Infecté",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/b45c8d08842925dadf1c1d8f5db72c153ddba37583e2b7b22225efc2d678d15e._RI_TTW_SX480_FMwebp_.png",
                        "Joel, Tess et Ellie traversent un hôtel de Boston abandonné et inondé pour déposer Ellie avec un groupe de lucioles.",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "Longtemps...",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/50622cbbadcec99dfe7e41f036953f1c7b235790e14ff791d9fe0b2cd0a69fa0._RI_TTW_SX720_FMwebp_.jpg",
                        "Lorsqu'un étranger s'approche de son enceinte, le survivaliste Bill forge une connexion improbable. Plus tard, Joel et Ellie cherchent les conseils de Bill.",
                        LocalDate.now()
                ),
                new Episode(
                        4,
                        "S'il te plaît, tiens ma main",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/79ed878337aaff85266474d72765605116a17a87aa1152a196472057527918ce._RI_TTW_SX720_FMwebp_.jpg",
                        "Après avoir entrepris un périple éreintant dans ce qui reste des États-Unis, Joel et Ellie doivent traverser Kansas City à pied. Plus tard, la cheffe rebelle Kathleen lance une chasse à l'homme qui oppose sa cruelle milice civile aux plus grands espoirs de l'humanité.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "La survie à tout prix",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/8fe246f4f8509f670a93e698fbbb7b6eaadcc461cad4070070762a5731509fe3._RI_TTW_SX720_FMwebp_.jpg",
                        "Kansas City à présent libre, Henry lutte pour protéger son frère Sam des soldats rebelles prêts à tout pour satisfaire leur cruel chef. La chasse à l'homme continue tandis que la soif de vengeance de Kathleen s'intensifie. Pendant ce temps, Ellie noue une amitié importante.",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "Proches",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/63d31dc37461b1c2fc832b5c740aff6d44530fef818cd4670b172beefb15c07d._RI_TTW_SX720_FMwebp_.jpg",
                        "Après des mois de voyage, Joel et Ellie reçoivent un sinistre avertissement sur ce qui les attend. Plus tard, Ellie en apprend plus sur le passé de Joel, tandis que ce dernier préférerait l'oublier.",
                        LocalDate.now()
                ),
                new Episode(
                        7,
                        "Abandonner",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/d9361ddcf511fbbc0091537dca34446d0eea02304594a3f7feeffb5f431adbc3._RI_TTW_SX720_FMwebp_.jpg",
                        "Alors qu'une pandémie a détruit la civilisation, un survivant aguerri vient en aide à une jeune fille de 14 ans, qui pourrait bien être le dernier espoir de l'humanité.",
                        LocalDate.now()
                ),
                new Episode(
                        8,
                        "Quand on est dans le besoin",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/251c1730ca98d906f27ea04788eb878bf3a0f601a60a46cb4186d9fed6ddbd62._RI_TTW_SX720_FMwebp_.jpg",
                        "Après avoir croisé la route d'un groupe de survivants en quête de vengeance, Ellie et sa combativité attirent l'attention de leur chef. Joel, affaibli et toujours en train de lutter pour survivre, doit affronter un groupe de chasseurs assoiffés de sang.",
                        LocalDate.now()
                ),
                new Episode(
                        9,
                        "Cherchez la lumière",
                        1,
                        "https://images-eu.ssl-images-amazon.com/images/S/pv-target-images/7aa6167b015734f3e8ecaf66e42b1411e61a7e9a32c0d27d96fc91936e1fdc07._RI_TTW_SX720_FMwebp_.jpg",
                        "Après avoir été poursuivie par les Infectés, Anna, enceinte, décide de faire confiance à un ami de longue date. Des années plus tard, Ellie soit supporter le poids émotionnel de son aventure, tandis que Joel doit lui aussi prendre une décision lourde de conséquences.",
                        LocalDate.now()
                )

        );

        for (Episode episodeInfo: episodesList) {
            episodeInfo.setSerie(savedSerie);
            episodeRepository.save(episodeInfo);
        }

        serieRepository.save(savedSerie);
    }

    private void createPeakyBlinders() {
        Serie serie = new Serie();
        serie.setIsCompleted(false);
        serie.setReleaseDate(LocalDate.now());
        serie.setPictureUrlXL("https://www.parismatch.com/lmnr/var/pm/public/media/image/2022/03/07/08/Peaky-Blinders-Le-retour-du-gang-de-Birmingham.jpg?VersionId=VhOk4ubaKL.NEj_4n4agkRbhcKJ9J.hn");
        serie.setPictureUrlXS("https://fr.web.img3.acsta.net/pictures/22/06/07/11/57/5231272.jpg");
        serie.setDescription("Birmingham, en 1919. Un gang familial règne sur un quartier de la ville : les Peaky Blinders, ainsi nommés pour les lames de rasoir qu'ils cachent dans la visière de leur casquette.");
        serie.setName("Peaky Blinders");
        serie.setProducer("Steven Knight");
        serie.setTrailerURL("https://www.youtube.com/embed/oVzVdvGIC7U?si=ykiI1o8bozU76LOm");

        Serie savedSerie = serieRepository.save(serie);

        List<String> categoriesList = Arrays.asList(
                "Drama",
                "Crime",
                "Histoire",
                "Années 1920",
                "Angleterre",
                "Gangsters",
                "Famille",
                "Ambition",
                "Pouvoir",
                "Violence"
        );
        List<Category> savedCategories = new ArrayList<>();
        for (String categoryName : categoriesList) {
            Category existingCategory = categoryRepository.findByName(categoryName);
            if (existingCategory != null) {
                savedSerie.getCategories().add(existingCategory);
            } else {
                Category newCategory = new Category(categoryName);
                savedCategories.add(categoryRepository.save(newCategory));
                savedSerie.getCategories().add(newCategory);
            }
        }

        List<Actor> actorsList = Arrays.asList(
                new Actor("Cillian", "Murphy",
                        "https://media.senscritique.com/media/000020675785/125x166/cillian_murphy.png"),
                new Actor("Paul", "Anderson",
                        "https://media.senscritique.com/media/000020675778/125x166/paul_anderson.png"),
                new Actor("Sophie", "Rundle",
                        "https://media.senscritique.com/media/000020675781/125x166/sophie_rundle.png"),
                new Actor("Helen", "McCrory",
                        "https://media.senscritique.com/media/000020017955/125x166/helen_mc_crory.jpg"),
                new Actor("Ned", "Dennehy",
                        "https://media.senscritique.com/media/000020675784/125x166/ned_dennehy.png"),
                new Actor("Finn", "Cole",
                        "https://media.senscritique.com/media/000017694984/125x166/Finn_Cole.jpg")
        );
        List<Actor> savedActors = new ArrayList<>();
        for (Actor actorInfo: actorsList) {
            savedActors.add(actorRepository.save(actorInfo));
            savedSerie.getActors().add(actorInfo);
        }

        List<Episode> episodesList = Arrays.asList(
                new Episode(
                        1,
                        "Épisode 1",
                        1,
                        "https://media.senscritique.com/media/000009589357/200/episode_1.jpg",
                        "Birmingham, 1919. Un gang familial règne sur un quartier de la ville : les Peaky Blinders, ainsi nommés pour les lames de rasoir qu’ils cachent dans la visière de leurs casquettes. Paris de bookmakers, marché noir et vols divers sont leur lot quotidien. Mais leur activité prend une dimension nouvelle lorsque leur chef, Thomas Shelby, récupère une cargaison d’armes de l’usine locale.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Épisode 2",
                        1,
                        "https://media.senscritique.com/media/000009589367/200/episode_2.jpg",
                        "Pendant que les frères Shelby sont à la foire et ont délaissé le quartier, les troupes de l’inspecteur Campbell fouillent chaque maison à la recherche d’armes. Par vengeance, Thomas Shelby organise un autodafé des portraits du roi en place publique. Winston Churchill apprend l’affaire et morigène Campbell. Mais Thomas Shelby a d’autres projets en tête : il propose un marché à l’inspecteur – armes contre protection – et rêve de s’associer avec Billy Kimber, un puissant bookmaker...",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "Épisode 3",
                        1,
                        "https://media.senscritique.com/media/000009589373/200/episode_3.jpg",
                        "Ada et Freddie, désormais mariés, vivent dans la clandestinité à Birmingham, ce qui déplaît fortement à Thomas car cela risque de compromettre son contrat avec l’inspecteur Campbell. Les Peaky Blinders ayant été approchés par des nationalistes irlandais à la recherche d’armes, Grace décide d’enquêter sur le compte de ces derniers et tue l’un d’entre eux en état de légitime défense. De son côté, Freddie continue de prêcher la cause communiste auprès des ouvriers. La police avertit Thomas que s’il ne livre pas son beau-frère, Ada sera également arrêtée, ce qui provoque de fortes tensions entre lui et la tante Polly." +
                                "\n",
                        LocalDate.now()
                ),new Episode(
                        4,
                        "Épisode 4",
                        1,
                        "https://media.senscritique.com/media/000009589377/200/episode_4.jpg",
                        "Pour protéger Freddie et Ada, Thomas et Polly font diversion en donnant à Campbell l’adresse de Stanley Chapman, un autre “camarade”. Mais sous la torture, celui-ci finit par trahir Freddie. Pendant ce temps, un conflit ouvert s’est déclaré avec un groupe de gitans, qui attaque la boutique familiale puis laisse une grenade dans la voiture de Thomas. Celui-ci trouve un arrangement en mariant son petit frère John à une jeune gitane, Esme Lee. Quant à Grace, elle commence à réunir des informations substantielles sur les transactions illégales de Thomas, notamment grâce à Arthur, qui gère désormais le pub.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "Épisode 5",
                        1,
                        "https://media.senscritique.com/media/000009589383/200/episode_5.jpg",
                        "Thomas confie à Grace son intention de renverser Billy Kimber. Seule au courant, elle promet de garder le secret. Elle découvre finalement où Thomas a caché les armes après une indiscrétion d’Arthur concernant la tombe de Danny Whizzbang, qui serait toujours bien en vie. Le père Shelby refait surface en ville. Thomas et Polly souhaitent qu’il reparte, mais Arthur, le fils aîné, s’enchante du retour de son paternel, d’autant que ce dernier lui annonce sa volonté de monter un casino familial. Confiant, Arthur n’hésite pas à lui donner de l’argent, mais il finit humilié quand il découvre la vraie nature de son père...",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "Épisode 6",
                        1,
                        "https://media.senscritique.com/media/000009589394/200/episode_6.jpg",
                        "Thomas a décidé de renverser Billy Kimber. Alors qu’il met en place son plan d’attaque, Campbell se rend dans un bordel. C’est là que Thomas le confronte. Campbell lui promet qu’il aura le coeur brisé avant la fin de la journée. Polly demande à Ada de pardonner à son frère, car ce dernier s’est engagé à faire libérer Freddie. Grace se sent coupable vis-à-vis de Thomas et décide de se confesser à de lui, mais elle est interrompue. Les hommes de Kimber sont en route après avoir découvert les intentions des Peaky Blinders. Les deux gangs se retrouvent face à face...",
                        LocalDate.now()
                ),
                new Episode(
                        1,
                        "Épisode 1",
                        2,
                        "https://media.senscritique.com/media/000009589399/200/episode_1.jpg",
                        "Birmingham, 1922. Désormais prospère, le gang des Shelby, les Peaky Blinders, règne sur les paris clandestins et la contrebande d’alcool avec la complicité de la police locale. Chef incontesté du clan familial, Tommy a décidé de conquérir Londres et de se tailler une place entre deux redoutables rivaux, en guerre l’un contre l’autre : le juif Alfie Solomon et l’Italien Darby Sabini. Son plan, risqué, consiste à proposer une alliance au premier, en perte de vitesse, contre le second. Mais alors que les Shelby sont réunis au cimetière pour enterrer l’un des leurs, leur pub est incendié.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Épisode 2",
                        2,
                        "https://media.senscritique.com/media/000009589404/200/episode_2.jpg",
                        "Le major Campbell a monté un nouveau plan contre Tommy et le fait filer jour et nuit. Il lui a ainsi sauvé la vie alors que les hommes de main de Sabini allaient l’achever. Sérieusement blessé, Tommy se rend quand même en secret à Londres pour conclure son alliance. En parallèle, il enquête pour retrouver les enfants de Polly, qui lui ont été retirés très jeunes, et doit une fois encore gérer un désastre causé par Arthur, sujet à d’incontrôlables accès de violence.",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "Épisode 3",
                        2,
                        "https://media.senscritique.com/media/000009589409/200/episode_3.jpg",
                        "Le pub familial a rouvert. Michael, le fils de Polly, que Tommy a réussi à retrouver, est venu de lui-même à la rencontre de sa mère, et le partenariat Shelby-Solomon peut se mettre en place à Londres, notamment sur les champs de courses. En cherchant à acheter un cheval, Tommy rencontre May Carleton, jeune aristocrate aussi belle qu’affranchie. Mais sa guerre personnelle avec le sanguinaire boss italien lui laisse peu de loisirs. D’autant que la famille, comme toujours, s’avère moins fiable qu’il ne le faudrait…",
                        LocalDate.now()
                ),
                new Episode(
                        4,
                        "Épisode 4",
                        2,
                        "https://media.senscritique.com/media/000009589414/200/episode_4.jpg",
                        "Arthur s’est installé à Londres et a pris les rênes du club londonien arraché au gang de Sabini. Prêt à tout pour le récupérer, ce dernier propose à son explosif adversaire, Solomon, d’unir leurs forces pour se débarrasser des Peaky Blinders. Tommy, lui, a embauché May Carleton pour entraîner son cheval de course, Grace’s Secret. Ils ne tardent pas à se rapprocher.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "Épisode 5",
                        2,
                        "https://media.senscritique.com/media/000009589419/200/episode_5.jpg",
                        "Avec l’arrestation d’Arthur et de Michael, le nouveau fief londonien des Peaky Blinders est plus que menacé. Le major Campbell les tient tous sous sa coupe. Affolée de voir son fils risquer une longue peine de prison, Polly se compromet pour garantir la liberté de Michael. Alors qu’il est sur le point de prendre des mesures radicales pour tenter de reprendre le contrôle de la situation, Tommy reçoit une visite venue du passé.",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "Épisode 6",
                        2,
                        "https://media.senscritique.com/media/000009589424/200/episode_6.jpg",
                        "C’est à l’hippodrome d’Epsom que Tommy doit accomplir l’assassinat exigé par Campbell. Le jour dit, il espère échapper à son vieil ennemi et l’emporter du même coup sur les Italiens. Mais il n’est pas le seul à avoir peaufiné un plan d’attaque.",
                        LocalDate.now()
                ),
                new Episode(
                        1,
                        "Épisode 1",
                        3,
                        "https://media.senscritique.com/media/000015340466/200/episode_1.jpg",
                        "1924. Tout le clan Shelby, dont les affaires sont de plus en plus prospères, est réuni à Birmingham pour le mariage de Tommy avec Grace. Alors que la fête, somptueuse, bat son plein, un invité mystérieux exige d'être reçu par Tommy, se prévalant d'une promesse ancienne et du soutien de Winston Churchill en personne. C'est l'aboutissement de tractations financières que le chef des Peaky Blinders tente depuis des mois de faire aboutir, sans se douter de la vraie nature de l'accord qu'il s’apprête à conclure. Déterminé à poursuivre son ascension vers les hautes sphères de la société, tout en assurant la sécurité des siens, le jeune marié va se retrouver piégé dans une machination aux ramifications internationales.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Épisode 2",
                        3,
                        "https://media.senscritique.com/media/000015340476/200/episode_2.jpg",
                        "Sur l'ordre de la société secrète dirigée par l'inquiétant pasteur John Hugues, Tommy rencontre à Londres un Russe blanc excentrique, le duc Romanov. Il se rend compte alors que ceux qui le tiennent en leur pouvoir, et exigent qu'il prépare un vol à main armée de grande ampleur, sont prêts à tout pour arriver à leurs fins. Pendant ce temps, à Birmingham, le tempérament explosif de John déclenche une sanglante guerre avec le clan italien des Changretta.",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "Épisode 3",
                        3,
                        "https://media.senscritique.com/media/000015496562/200/episode_3.jpg",
                        "Tandis que Tommy se laisse aveugler par son désir de vengeance, sa famille est au bord de l'implosion. La tante Polly, déstabilisée par sa rencontre avec un peintre plein de charme, ne lui est d'aucun secours, et Arthur, sous l'influence de sa vertueuse épouse, rêve de rédemption. L'aîné des Shelby consent quand même, au côté de John, à régler une fois pour toutes le conflit ouvert avec les Changretta. Peu après, Tommy rencontre la véritable instigatrice du complot dans lequel il est impliqué malgré lui : il s'agit d'une autre émigrée russe, une jeune duchesse aussi belle qu'intrépide;",
                        LocalDate.now()
                ),
                new Episode(
                        4,
                        "Épisode 4",
                        3,
                        "https://media.senscritique.com/media/000015771912/200/episode_4.jpg",
                        "Lors d'une partie de chasse avec ses frères, Tommy partage une nouvelle importante pour la famille. Il détaille aussi les plans de son prochain coup, d'une ampleur inédite. Le jeu dangereux qu'il entretient avec la duchesse Tatiana se poursuit, chacun essayant de soutirer des informations à l'autre. La tante Polly, elle, semble sur le point de craquer et de révéler ce qu'elle a sur la conscience, ce qui ferait courir un terrible danger au chef des Peaky Blinders.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "Épisode 5",
                        3,
                        "https://media.senscritique.com/media/000015771913/200/episode_5.jpg",
                        "Tommy comprend qu'il ne joue pas dans la même cour que les Russes avec qui il cherche à s'associer. Il décide d'avoir une nouvelle fois recours aux services d'Alfie Solomons. Avant de sceller leur accord, les Russes mettent les Peaky Blinders à l'épreuve d'une manière peu orthodoxe. Alors que le grand jour approche, Tommy décide de faire appel à ses anciens frères d'armes, rescapés comme lui des tranchées de la Grande Guerre, en qui il a toute confiance.",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "Épisode 6",
                        3,
                        "https://media.senscritique.com/media/000015851152/200/episode_6.jpg",
                        "Tandis que Tommy s'apprête à enclencher l'opération la plus audacieuse de sa carrière, son fils est kidnappé par le père Hugues. Aux abois, obsédé par l'idée que le sang de ses crimes ne peut que retomber sur la tête de son enfant, le chef des Peaky Blinders se lance dans une course désespérée pour le sauver.",
                        LocalDate.now()
                ),
                new Episode(
                        1,
                        "La corde au cou",
                        4,
                        "https://media.senscritique.com/media/000017407038/200/la_corde_au_cou.jpg",
                        "Noël 1925. La famille Shelby, déchirée, apprend que la mafia new-yorkaise est déterminée à se venger de meurtres commis l'année précédente.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Sauvages",
                        4,
                        "https://media.senscritique.com/media/000017407039/200/sauvages.jpg",
                        "Toujours sous le choc, les Shelby se rassemblent à Small Heath, où Tommy appelle à la trêve familiale le temps d'une offensive contre la mafia.",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "Blackbird",
                        4,
                        "https://media.senscritique.com/media/000017435622/200/blackbird.jpg",
                        "Alors que Linda tente de distraire Arthur, ce dernier se retrouve pris au piège. Tommy est confronté à son passé. Polly retrouve une vieille connaissance.",
                        LocalDate.now()
                ),
                new Episode(
                        4,
                        "Dangereuse",
                        4,
                        "https://media.senscritique.com/media/000017435623/200/dangereuse.jpg",
                        "La mafia tente d'attirer un membre de la famille Shelby dans son piège. Tommy reçoit la visite d'une ancienne conquête, et Lizzie fait une découverte inattendue.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "Le duel",
                        4,
                        "https://media.senscritique.com/media/000017452156/200/le_duel.jpg",
                        "Tommy décide de déclencher à lui seul une fusillade contre la mafia. Luca Changretta se rapproche d'Alfie Solomons. L'armée britannique part à la recherche d'Ada.",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "La société",
                        4,
                        "https://media.senscritique.com/media/000017465279/200/la_societe.jpg",
                        "Arthur décide de suivre son instinct lors d'un combat de boxe, tandis que Tommy s'inspire des paroles de sagesse d'Alfie Solomons et reçoit de précieuses informations...",
                        LocalDate.now()
                ),
                new Episode(
                        1,
                        "Le Mardi noir",
                        5,
                        "https://media.senscritique.com/media/000018685057/200/le_mardi_noir.jpg",
                        "De stupéfiantes nouvelles des États-Unis déclenchent une réunion au sommet. Ada cache un secret. Un journaliste rappelle à Tommy son passé.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Mauvais augures",
                        5,
                        "https://media.senscritique.com/media/000018685058/200/mauvais_augures.jpg",
                        "Les messages anonymes prennent une tournure terrifiante. La loyauté de Michael est mise en doute. Tommy reçoit une proposition d'Oswald Mosley.",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "Stratégies",
                        5,
                        "https://media.senscritique.com/media/000018698980/200/strategies.jpg",
                        "Aberama se rend à Glasgow pour se venger. Mosley fait chanter Tommy, qui décide de renverser la situation. Arthur perd tout contrôle en s'acharnant à retrouver Linda.",
                        LocalDate.now()
                ),
                new Episode(
                        4,
                        "La Boucle",
                        5,
                        "https://media.senscritique.com/media/000018726656/200/la_boucle.jpg",
                        "Tommy appelle à une trêve et rencontre Jimmy McCavern. Une opportunité commerciale lucrative se présente. Tommy passe un marché avec Aberama.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "Boum",
                        5,
                        "https://media.senscritique.com/media/000018741329/200/boum.jpg",
                        "Mosley prononce un discours à la fête. Ben Younger endure les conséquences de sa trahison. Tommy rend visite à un patient dans un asile psychiatrique.",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "Monsieur Jones",
                        5,
                        "https://media.senscritique.com/media/000018769062/200/monsieur_jones.jpg",
                        "Michael profite d'une réunion de famille pour annoncer un projet audacieux. La taupe est identifiée. Tommy demande l'aide d'un ancien rival pour exécuter son plan.",
                        LocalDate.now()
                ),
                new Episode(
                        1,
                        "Une journée bien noire",
                        6,
                        "https://media.senscritique.com/media/000020560140/200/une_journee_bien_noire.jpg",
                        "L'assassinat manqué de Mosley coûte un membre à la famille Shelby. Quatre ans plus tard, la fin imminente de la prohibition amène Tommy en Amérique du Nord.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Équivoque",
                        6,
                        "https://media.senscritique.com/media/000020560139/200/equivoque.jpg",
                        "En Angleterre, Tommy forge une alliance improbable afin d'inciter Jack Nelson à accepter sa proposition. Ruby, la cadette de Tommy, fait une déclaration dérangeante",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "L'or",
                        6,
                        "https://media.senscritique.com/media/000020588213/200/l_or.jpg",
                        "Tommy se précipite chez Esme. Prenant sa place, Ada présente Jack Nelson à Mosley et Diana. Arthur rencontre un ancien toxicomane qui comprend ses difficultés.",
                        LocalDate.now()
                ),
                new Episode(
                        4,
                        "Le saphir bleu",
                        6,
                        "https://media.senscritique.com/media/000020599833/200/le_saphir_bleu.jpg",
                        "Anéanti par une nouvelle disparition, Tommy décide de se venger. Ada est victime de la violence fasciste. Une lettre urgente arrive du sanatorium.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "La rédemption ou l'enfer",
                        6,
                        "https://media.senscritique.com/media/000020616038/200/la_redemption_ou_l_enfer.jpg",
                        "Tommy passe un marché avec Linda. La taupe infiltrée chez les Shelby reçoit une nouvelle mission. Diana accepte de rendre service à Tommy… à une condition.",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "Les vagabonds et les voleurs",
                        6,
                        "https://media.senscritique.com/media/000020616033/200/les_vagabonds_et_les_voleurs.jpg",
                        "La taupe découvre où et quand trouver Arthur seul. Sorti de prison, Michael n'a qu'une idée en tête. La loyauté de Finn vis-à-vis des Shelby est mise à l'épreuve.",
                        LocalDate.now()
                )
        );
        for (Episode episodeInfo: episodesList) {
            episodeInfo.setSerie(savedSerie);
            episodeRepository.save(episodeInfo);
        }

        serieRepository.save(savedSerie);
    }

    private void createArcane() {
        Serie serie = new Serie();
        serie.setIsCompleted(false);
        serie.setReleaseDate(LocalDate.now());
        serie.setPictureUrlXL("https://images6.alphacoders.com/118/thumb-1920-1188254.jpg");
        serie.setPictureUrlXS("https://fr.web.img6.acsta.net/pictures/21/11/02/11/12/2878509.jpg");
        serie.setDescription("Au milieu du conflit entre les villes jumelles de Piltover et Zaun, deux soeurs se battent dans les camps opposés d'une guerre entre technologies magiques et convictions incompatibles.");
        serie.setName("Arcane");
        serie.setProducer("Alex Yee, Christian Linke");
        serie.setTrailerURL("https://www.youtube.com/embed/fXmAurh012s?si=uwJWt-laq8Bdl6_O");

        Serie savedSerie = serieRepository.save(serie);

        List<String> categoriesList = Arrays.asList(
                "Action",
                "Aventure",
                "Animation",
                "Science-fiction",
                "Fantastique",
                "Drame",
                "Thriller",
                "Guerre"
        );
        List<Category> savedCategories = new ArrayList<>();
        for (String categoryName : categoriesList) {
            Category existingCategory = categoryRepository.findByName(categoryName);
            if (existingCategory != null) {
                savedSerie.getCategories().add(existingCategory);
            } else {
                Category newCategory = new Category(categoryName);
                savedCategories.add(categoryRepository.save(newCategory));
                savedSerie.getCategories().add(newCategory);
            }
        }

        List<Actor> actorsList = Arrays.asList(
                new Actor("Hailee", "Steinfeld",
                        "https://media.senscritique.com/media/000020267659/125x166/hailee_steinfeld.png"),
                new Actor("Alice", "Taurand",
                        "https://media.senscritique.com/media/000007862508/125x166/Alice_Taurand.png"),
                new Actor("Ella", "Purnell",
                        "https://media.senscritique.com/media/000019101008/125x166/Ella_Purnell.png"),
                new Actor("Mia Sinclair", "Jenness",
                        "https://media.senscritique.com/media/000020710980/125x166/mia_sinclair_jenness.jpg"),
                new Actor("Alice", "Orsat",
                        "https://media.senscritique.com/media/000020710973/125x166/alice_orsat.jpg"),
                new Actor("Adeline", "Chetail",
                        "https://media.senscritique.com/media/000020710968/125x166/adeline_chetail.jpg")
        );
        List<Actor> savedActors = new ArrayList<>();
        for (Actor actorInfo: actorsList) {
            savedActors.add(actorRepository.save(actorInfo));
            savedSerie.getActors().add(actorInfo);
        }

        List<Episode> episodeList = Arrays.asList(
                new Episode(
                        1,
                        "Welcome to the playground",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BMDI5YzBjYzctMDFkMS00Y2M1LTg5YWItMzA0ODNmOTgzYWViXkEyXkFqcGdeQXVyMTEzMTI1Mjk3._V1_UY126_CR36,0,224,126_AL_.jpg",
                        "Les sœurs orphelines Vi et Powder causent des remous dans les rues souterraines de Zaun à la suite d'un braquage dans le très huppé Piltover.",
                        LocalDate.now()
                ),
                new Episode(
                        2,
                        "Certains mystères ne devraient jamais être résolus",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BMDE0NzBkYmEtZDI5Yy00NWFhLTk4MWYtYmVmZmUxODY4YjczXkEyXkFqcGdeQXVyODc5MTI0NjU@._V1_UY126_UX224_AL_.jpg",
                        "Idéaliste, le chercheur Jayce tente de maîtriser la magie par la science malgré les avertissements de son mentor. Le criminel Silco teste une substance puissante.",
                        LocalDate.now()
                ),
                new Episode(
                        3,
                        "Cette violence crasse nécessaire au changement",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BNmEzZDM0ODgtZWNjMC00ZjEzLWEzMjEtNWIwMTdiMWFmYTIzXkEyXkFqcGdeQXVyMjc5OTMxMzQ@._V1_UY126_CR36,0,224,126_AL_.jpg",
                        "Deux anciens rivaux s'affrontent lors d'un défi spectaculaire qui se révèle fatidique pour Zaun. Jayce et Viktor prennent de gros risques pour leurs recherches.",
                        LocalDate.now()
                ),
                new Episode(
                        4,
                        "Joyeuse Fête du progrès",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BN2MzZTRjZTYtNWZlZS00Yzk3LWE0ZjQtOTQzZmM0MDcyOWQ4XkEyXkFqcGdeQXVyMjc5OTMxMzQ@._V1_UY126_CR36,0,224,126_AL_.jpg",
                        "Alors que Piltover profite de leur technologie, Jayce et Viktor réfléchissent à ce qu'ils vont faire. Un visage familier ressort de Zaun pour semer la pagaille.",
                        LocalDate.now()
                ),
                new Episode(
                        5,
                        "L'ennemi commun",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BZTk3MzEyOTItNzcwOC00NzU2LWE3NTYtNTU3ZTUwNjAwMzNlXkEyXkFqcGdeQXVyODc5MTI0NjU@._V1_UY126_UX224_AL_.jpg",
                        "Caitlyn, la pacifieuse qui n'en fait qu'à sa tête, arpente les bas-fonds pour trouver Silco. Jayce devient une cible en combattant la corruption à Piltover.",
                        LocalDate.now()
                ),
                new Episode(
                        6,
                        "Quand l'empire s'effondre",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BNDVlZGYzODEtMDM0MC00ZWQ5LWFkNmEtNWFjZmE4Y2JhM2EyXkEyXkFqcGdeQXVyNjk1MjA0MTk@._V1_UY126_CR37,0,224,126_AL_.jpg",
                        "Un protégé discrédite son mentor devant le Conseil à cause de l'évolution rapide d'une technologie magique. Poursuivie par les autorités, Jinx doit affronter son passé.",
                        LocalDate.now()
                ),
                new Episode(
                        7,
                        "Le petit sauveur",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BODY0MmEyNTYtOGExMy00MjA0LTlkYzYtNzQ5OGI3MmM1MDU5XkEyXkFqcGdeQXVyODc5MTI0NjU@._V1_UY126_UX224_AL_.jpg",
                        "Caitlyn et Vi retrouvent un allié dans les rues de Zaun et se déchaînent contre une ennemie commune. Viktor prend une décision désespérée.",
                        LocalDate.now()
                ),
                new Episode(
                        8,
                        "L'eau et l'huile",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BYTJlZTBhMTYtMDJlYS00ZGI3LWE3NTQtYTYxNWI5NzI3Yjc2XkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_UY126_CR36,0,224,126_AL_.jpg",
                        "L'héritière désavouée Mel et sa mère échangent des tactiques de combat. Caitlyn et Vi forgent une alliance improbable. Jinx subit une étonnante transformation.",
                        LocalDate.now()
                ),
                new Episode(
                        9,
                        "Rouages et engrenages",
                        1,
                        "https://m.media-amazon.com/images/M/MV5BNDZmMjQ4MTYtYWVjZS00YzkxLWE5MTgtYjQwZTFiYWFlY2Q1XkEyXkFqcGdeQXVyMTQ0MTQ0NzQ3._V1_UY126_CR3,0,224,126_AL_.jpg",
                        "À deux doigts d'entrer en guerre, les chefs de Piltover et de Zaun sont face à un ultimatum, jusqu'à ce qu'un affrontement fatidique bouleverse les deux villes à jamais.",
                        LocalDate.now()
                )
        );
        for (Episode episodeInfo: episodeList) {
            episodeInfo.setSerie(savedSerie);
            episodeRepository.save(episodeInfo);
        }

        serieRepository.save(savedSerie);
    }
}
