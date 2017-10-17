package de.bonprix.service.basiclanguage;

import java.util.List;

import javax.annotation.Resource;

import de.bonprix.service.AbstractBasicLanguageContainerService;

public class BasicLanguageServiceImpl extends
		AbstractBasicLanguageContainerService<BasicLanguageEntity, BasicLanguageLanguageEntity, BasicLanguage, BasicLanguageLanguage, BasicLanguageFilter, BasicLanguageRepository>
		implements BasicLanguageService {

	public BasicLanguageServiceImpl() {
		super(BasicLanguageEntity.class, BasicLanguageLanguageEntity.class, BasicLanguage.class,
				BasicLanguageLanguage.class, BasicLanguageFilter.class);
	}

	@Resource
	private BasicLanguageRepository exampleLanguageRepository;

	@Override
	protected BasicLanguageRepository getRepository() {
		return this.exampleLanguageRepository;
	}

	@Override
	public List<BasicLanguage> findAll(BasicLanguageFilter filter) {
		return super.findAllDefault(filter);
	}

	@Override
	public BasicLanguage findById(Long id) {
		return super.findByIdDefault(id);
	}

	@Override
	public void deleteById(Long id) {
		super.deleteByIdDefault(id);
	}

	@Override
	public long create(BasicLanguage basicLanguage) {
		return super.createDefault(basicLanguage);
	}

	@Override
	public void update(Long id, BasicLanguage basicLanguage) {
		super.updateDefault(id, basicLanguage);
	}

}
