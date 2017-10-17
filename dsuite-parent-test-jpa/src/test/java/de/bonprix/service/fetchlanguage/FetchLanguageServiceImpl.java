package de.bonprix.service.fetchlanguage;

import java.util.List;

import javax.annotation.Resource;

import de.bonprix.service.AbstractFetchLanguageContainerService;

public class FetchLanguageServiceImpl extends
		AbstractFetchLanguageContainerService<FetchLanguageEntity, FetchLanguageLanguageEntity, FetchLanguage, FetchLanguageLanguage, FetchLanguageFilter, FetchLanguageFetchOptions, FetchLanguageRepository>
		implements FetchLanguageService {

	public FetchLanguageServiceImpl() {
		super(FetchLanguageEntity.class, FetchLanguageLanguageEntity.class, FetchLanguage.class,
				FetchLanguageLanguage.class, FetchLanguageFilter.class, FetchLanguageFetchOptions.class);
	}

	@Resource
	private FetchLanguageRepository fetchLanguageRepository;

	@Override
	protected FetchLanguageRepository getRepository() {
		return this.fetchLanguageRepository;
	}

	@Override
	public List<FetchLanguage> findAll(FetchLanguageFilter filter, FetchLanguageFetchOptions fetchOptions) {
		return super.findAllDefault(filter, fetchOptions);
	}

	@Override
	public FetchLanguage findById(Long id, FetchLanguageFetchOptions fetchOptions) {
		return super.findByIdDefault(id, fetchOptions);
	}

	@Override
	public void deleteById(Long id) {
		super.deleteByIdDefault(id);
	}

	@Override
	public long create(FetchLanguage fetchLanguage) {
		return super.createDefault(fetchLanguage);
	}

	@Override
	public void update(Long id, FetchLanguage fetchLanguage) {
		super.updateDefault(id, fetchLanguage);
	}

}
