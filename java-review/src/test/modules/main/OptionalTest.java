package test.modules.main;

import java.util.Optional;

public class OptionalTest {
	public static void main(String args[]) {
		System.out.println("Optional test");
		optionalTest();
	}
	
	private static void optionalTest() {
		Computer c1 = new Computer(
						Optional.of(new Soundcard(Optional.of("sb awe32"))));
		Computer c2 = new Computer(Optional.empty());
		Computer c3 = new Computer(Optional.of(new Soundcard(Optional.empty())));

		System.out.println(getSoundcardName(c1));
		System.out.println(getSoundcardName(c2));
		System.out.println(getSoundcardName(c3));
	}
	
	private static String getSoundcardName(Computer c) {
		//why not just c?.getSoundCard()?.getName()
		return Optional.of(c).flatMap(Computer::getSoundcard).flatMap(Soundcard::getName).orElse("Unnnamed soundcard");
	}
}

class Computer {
	private Optional<Soundcard> soundcard = Optional.empty();
	public Computer(Optional<Soundcard> soundcard) {
		this.soundcard = soundcard;
	}
	public Optional<Soundcard> getSoundcard() {
		if (soundcard.isEmpty()) {
			this.soundcard = Optional.of(Soundcard.NO_SOUNDCARD); //Not a good idea, this makes it so there are two 'empty' values. Just tried it out to see if I can do some branching on the getSoundcardName method
		}
		return soundcard;
	}
}

class Soundcard {
	public static Soundcard NO_SOUNDCARD = new Soundcard(Optional.of("No soundcard"));
	
	private Optional<String> name = Optional.empty();
	public Soundcard(Optional<String> name) {
		this.name = name;
	}
	public Optional<String> getName() {
		return name;
	}
	public void setName(String name) {
		this.name = Optional.of(name);
	}
}

