package org.javabip.spec.petrinets;

import org.javabip.glue.GlueBuilder;

public class PetriNetGlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		port(AtomPetri1.class, "get").requires(PetriResouce.class,"get1");
		port(AtomPetri1.class, "free").requires(PetriResouce.class,"free1");
		port(AtomPetri1.class, "sync").requires(AtomPetri2.class,"sync");
		port(AtomPetri2.class, "get").requires(PetriResouce.class,"get2");
		port(AtomPetri2.class, "free").requires(PetriResouce.class,"free2");
		port(AtomPetri2.class, "sync").requires(AtomPetri1.class,"sync");
		port(PetriResouce.class,"get1").requires(AtomPetri1.class, "get");
		port(PetriResouce.class,"get2").requires(AtomPetri2.class, "get");
		port(PetriResouce.class,"free1").requires(AtomPetri1.class, "free");
		port(PetriResouce.class,"free2").requires(AtomPetri2.class, "free");
		
		port(AtomPetri1.class, "get").accepts(PetriResouce.class,"get1");
		port(AtomPetri1.class, "free").accepts(PetriResouce.class,"free1");
		port(AtomPetri1.class, "sync").accepts(AtomPetri2.class,"sync");
		port(AtomPetri2.class, "get").accepts(PetriResouce.class,"get2");
		port(AtomPetri2.class, "free").accepts(PetriResouce.class,"free2");
		port(AtomPetri2.class, "sync").accepts(AtomPetri1.class,"sync");
		port(PetriResouce.class,"get1").accepts(AtomPetri1.class, "get");
		port(PetriResouce.class,"get2").accepts(AtomPetri2.class, "get");
		port(PetriResouce.class,"free1").accepts(AtomPetri1.class, "free");
		port(PetriResouce.class,"free2").accepts(AtomPetri2.class, "free");
		
	}

}
