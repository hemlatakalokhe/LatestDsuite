package de.bonprix.service.basic;

import java.util.List;

import javax.annotation.Resource;

import de.bonprix.service.AbstractBasicService;

public class BasicServiceImpl extends AbstractBasicService<BasicEntity, Basic, BasicFilter, BasicRepository>
		implements BasicService {

	public BasicServiceImpl() {
		super(BasicEntity.class, Basic.class, BasicFilter.class);
	}

	@Resource
	private BasicRepository exampleLanguageRepository;

	@Override
	protected BasicRepository getRepository() {
		return this.exampleLanguageRepository;
	}

	@Override
	public List<Basic> findAll(BasicFilter filter) {
		return super.findAllDefault(filter);
	}

	@Override
	public Basic findById(Long id) {
		return super.findByIdDefault(id);
	}

	@Override
	public void deleteById(Long id) {
		super.deleteByIdDefault(id);
	}

	@Override
	public long create(Basic basic) {
		return super.createDefault(basic);
	}

	@Override
	public void update(Long id, Basic basic) {
		super.updateDefault(id, basic);
	}

}
