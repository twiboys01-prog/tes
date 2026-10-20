// Memasang plugin native "GallerySaver" (simpan langsung ke Galeri) ke proyek Android
// yang dibuat otomatis oleh `npx cap add android`, plus kunci tanda tangan tetap.
// Dipanggil otomatis oleh hook "capacitor:sync:before" di package.json,
// jadi tetap jalan walau file workflow di GitHub belum diperbarui.
const fs = require('fs');
const os = require('os');
const path = require('path');

const root = path.resolve(__dirname, '..');
const androidDir = path.join(root, 'android');
if (!fs.existsSync(androidDir)) {
  console.log('[setup-native] folder android/ belum ada, dilewati');
  process.exit(0);
}

const cfg = JSON.parse(fs.readFileSync(path.join(root, 'capacitor.config.json'), 'utf8'));
const appId = cfg.appId || 'com.collagestudio.app';
const destDir = path.join(androidDir, 'app', 'src', 'main', 'java', ...appId.split('.'));
fs.mkdirSync(destDir, { recursive: true });

for (const f of ['GallerySaverPlugin.java', 'MainActivity.java']) {
  let src = fs.readFileSync(path.join(root, 'native', f), 'utf8');
  src = src.replace(/^package\s+[\w.]+;/m, `package ${appId};`);   // ikut appId sebenarnya
  fs.writeFileSync(path.join(destDir, f), src);
  console.log('[setup-native] dipasang:', path.join(destDir, f));
}

const ks = path.join(root, 'native', 'debug.keystore');
if (fs.existsSync(ks)) {
  const dst = path.join(os.homedir(), '.android', 'debug.keystore');
  fs.mkdirSync(path.dirname(dst), { recursive: true });
  fs.copyFileSync(ks, dst);
  console.log('[setup-native] keystore tetap dipasang');
}
