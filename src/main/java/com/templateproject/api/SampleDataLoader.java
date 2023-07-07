package com.templateproject.api;

import com.github.javafaker.Faker;
import com.templateproject.api.entity.*;
import com.templateproject.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
        List<Actor> actor = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> new Actor(
                        faker.name().firstName(), faker.name().lastName(), faker.internet().avatar()
                )).collect(Collectors.toList());
        actorRepository.saveAll(actor);

        List<Category> category = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Category(
                        faker.book().genre()
                )).collect(Collectors.toList());
        categoryRepository.saveAll(category);

        List<Serie> series = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> {
                            Serie serie = new Serie(
                                    faker.book().title(),
                                    faker.book().publisher(),
                                    faker.internet().avatar(),
                                    faker.internet().url(),
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
                    episode.setThumbnail(faker.internet().avatar());
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

        List<User> users = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> {
                    User user = new User(
                            faker.internet().emailAddress(),
                            faker.animal().name(),
                            faker.internet().password(),
                            faker.internet().avatar(),
                            i == 1 ? roleAdmin : roleUser
                    );
                    return user;
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
