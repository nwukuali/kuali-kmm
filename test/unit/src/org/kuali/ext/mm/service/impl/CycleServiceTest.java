package org.kuali.ext.mm.service.impl;

import edu.msu.ebsp.common.sys.context.MmTestBase;

//public class CycleServiceTest extends AbstractJpaTests {
public class CycleServiceTest extends MmTestBase {
/*
	private CycleService cycleService;
	private String cycleCntCode = "Z";

	public void setCycleService(CycleService cycleService) {
		this.cycleService = cycleService;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.cycleService = (CycleService) SpringContext
				.getBean(CycleService.class);
		Cycle cycle1 = new Cycle("A", "firstCycleTest", (long) 10, new Timestamp(System.currentTimeMillis (  )),
				"Y", "naser", new Timestamp(System.currentTimeMillis (  )));
		Cycle cycle2 = new Cycle("B", "secondCycleTest", (long) 20, new Timestamp(System.currentTimeMillis (  )),
				"Y", "naser2", new Timestamp(System.currentTimeMillis (  )));
		Cycle cycle3 = new Cycle("C", "thirdCycleTest", (long) 30, new Timestamp(System.currentTimeMillis (  )),
				"Y", "naser3", new Timestamp(System.currentTimeMillis (  )));

		cycleService.save(cycle1);
		cycleService.save(cycle2);
		cycleService.save(cycle3);
		cycleCntCode = cycle1.getCycleCntCd();
	}

	public void testFindByCycleId() {
		Cycle cycle = cycleService.findById(cycleCntCode);
		assertNotNull(cycle);
		assertEquals("A", cycle.getCycleCntCd());
	}

	public void testFindByCycleIdBadId() {
		Cycle cycle = cycleService.findById("Z");
		assertNull(cycle);
	}

	public void testFindByCycleNum() {
		List<Cycle> cycles = cycleService.findByCycleCode("B");
		assertEquals(1, cycles.size());
		Cycle cycle = cycles.get(0);
		assertEquals("secondCycleTest", cycle.getCycleCntDesc());
	}

	public void testFindByCycleNumBadNum() {
		List<Cycle> cycles = cycleService.findByCycleCode("Z");
		assertEquals(0, cycles.size());
	}

	public void testModifyCycle() {
		String oldDescription = "firstCycleTest";
		String newDescription = "firstCycleTestModified";
		Cycle cycle = cycleService.findByCycleCode("A").get(0);
		cycle.setCycleCntDesc(newDescription);

		Cycle cycle2 = cycleService.update(cycle);
		assertEquals(newDescription, cycle2.getCycleCntDesc());

	}

	public void testDeleteCycle() {
		Cycle cycle = cycleService.findByCycleCode("A").get(0);
		cycleService.delete(cycle);
		List<Cycle> res = cycleService.findByCycleCode("A");
		assertEquals(0, res.size());

	}

	public void testFindAll() {
		List<Cycle> cycles = cycleService.findAll();
		assertEquals(3, cycles.size());
	}
*/
}
