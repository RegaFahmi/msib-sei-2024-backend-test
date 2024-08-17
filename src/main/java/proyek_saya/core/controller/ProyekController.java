package proyek_saya.core.controller;

import proyek_saya.core.exception.ResourceNotFoundException;
import proyek_saya.core.model.Lokasi;
import proyek_saya.core.model.Proyek;
import proyek_saya.core.repository.LokasiRepository;
import proyek_saya.core.repository.ProyekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/proyek")
public class ProyekController {

    @Autowired
    private ProyekRepository proyekRepository;

    @Autowired
    private LokasiRepository lokasiRepository;

    @PostMapping
    public Proyek createProyek(@RequestBody Proyek proyek) {
        List<Lokasi> lokasiList = lokasiRepository.findAllById(
                proyek.getLokasiList().stream().map(Lokasi::getId).toList()
        );
        proyek.setLokasiList(lokasiList);
        return proyekRepository.save(proyek);
    }

    @GetMapping
    public List<Proyek> getAllProyek() {
        return proyekRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyek> updateProyek(@PathVariable Long id, @RequestBody Proyek proyekDetails) {
        Proyek proyek = proyekRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyek tidak ditemukan"));

        proyek.setNamaProyek(proyekDetails.getNamaProyek());
        proyek.setClient(proyekDetails.getClient());
        proyek.setTglMulai(proyekDetails.getTglMulai());
        proyek.setTglSelesai(proyekDetails.getTglSelesai());
        proyek.setPimpinanProyek(proyekDetails.getPimpinanProyek());
        proyek.setKeterangan(proyekDetails.getKeterangan());

        List<Lokasi> lokasiList = lokasiRepository.findAllById(
                proyekDetails.getLokasiList().stream().map(Lokasi::getId).toList()
        );
        proyek.setLokasiList(lokasiList);

        final Proyek updatedProyek = proyekRepository.save(proyek);
        return ResponseEntity.ok(updatedProyek);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyek(@PathVariable Long id) {
        Proyek proyek = proyekRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyek tidak ditemukan"));

        proyekRepository.delete(proyek);
        return ResponseEntity.noContent().build();
    }
}
